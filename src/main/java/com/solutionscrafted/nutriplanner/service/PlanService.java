package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.dto.PlanRequestDto;
import com.solutionscrafted.nutriplanner.entity.Client;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import com.solutionscrafted.nutriplanner.entity.SportActivity;
import com.solutionscrafted.nutriplanner.entity.dayplan.*;
import com.solutionscrafted.nutriplanner.mappers.NutriMapper;
import com.solutionscrafted.nutriplanner.repository.ActivityRepository;
import com.solutionscrafted.nutriplanner.repository.ClientRepository;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanActivityRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {

    private final DayPlanActivityRepository dpaRepository;
    private final ActivityRepository activityRepository;
    private final PlanRepository planRepository;
    private final RecipeRepository recipeRepository;
    private final ClientRepository clientRepository;
    private final DayPlanRepository dayPlanRepository;
    private final DayPlanRecipeRepository dayPlanRecipeRepository;
    private final NutriMapper nutriMapper;

    public List<PlanDto> getPlansByClientId(Long id) {
        return nutriMapper.toPlanDtoList(planRepository.findByClient_Id(id));
    }

    public PlanDto updatePlan(Long id, PlanRequestDto requestDto) {
        //TODO: implement
        Plan plan = planRepository.findById(id).orElseThrow(() -> new RuntimeException("Plan not found for id: " + id));
        return nutriMapper.toDto(plan);
    }

    public Plan generatePlan(PlanRequestDto requestDto) {
        Client client = clientRepository.findById(requestDto.clientId().longValue()).orElseThrow(() -> new RuntimeException("Client not found!"));
        double totalCalories = requestDto.totalCalories();

        Plan plan = Plan.builder().title(requestDto.title()).description(requestDto.description()).totalCalories(totalCalories).numberOfDays(requestDto.numberOfDays()).dateCreated(LocalDateTime.now()).client(client).build();

        Plan planSaved = planRepository.save(plan);
        log.info("Plan saved: " + planSaved);

        //TODO: 1. Calculate activity for each day;
        // 2. Observation notes for each day;
        // 3. DayPlan for each day, splitting the total kcal between breakfast, lunch dinner snack
        List<DayPlan> dayPlanList = new ArrayList<>();
        Integer days = requestDto.numberOfDays();

        for (int i = 1; i <= days; i++) {
            DayPlan dayPlan = DayPlan.builder()
                    .day(i)
                    .plan(plan)
                    .build();

            DayPlan dayPlanSaved = dayPlanRepository.save(dayPlan);
            log.info("DayPlanSaved: {}.", dayPlanSaved);

            assignDailyActivities(dayPlanSaved);
            assignDailyRecipes(dayPlanSaved, totalCalories, requestDto.excludeTag());

            dayPlanList.add(dayPlanSaved);
        }

        planSaved.setDayPlans(dayPlanList);
        log.info("Generated plan: {}", plan);

        return planRepository.save(planSaved);
    }

    private void assignDailyActivities(DayPlan dayPlan) {
        List<SportActivity> allActivities = activityRepository.findAll();
        int numberOfActivities = 1; // change to 2–3 if needed

        Collections.shuffle(allActivities);

        for (int i = 0; i < numberOfActivities; i++) {
            SportActivity selected = allActivities.get(i);
            DayPlanActivityId id = new DayPlanActivityId(dayPlan.getId(), selected.getId());

            DayPlanActivity dpa = DayPlanActivity.builder().id(id).dayPlan(dayPlan).activity(selected).build();

            dpaRepository.save(dpa);
            dayPlanRepository.save(dayPlan);
        }
    }

    //TODO: here will create recipes for a whole day
    private void assignDailyRecipes(DayPlan dayPlan, Double target, String excludeTags) { //TODO: there is better idea for the tags to avoid filtering errors

        //add breakfast recipes
        List<DayPlanRecipe> breakfastRecipes = getSpecificRecipesForMeal(dayPlan, target, BREAKFAST, excludeTags);

        //add lunch recipes
        List<DayPlanRecipe> lunchRecipes = getSpecificRecipesForMeal(dayPlan, target, LUNCH, excludeTags);

        //add dinner recipes
        List<DayPlanRecipe> dinnerRecipes = getSpecificRecipesForMeal(dayPlan, target, DINNER, excludeTags);

        //add snack recipes -> optional
        List<DayPlanRecipe> snackRecipes = getSpecificRecipesForMeal(dayPlan, target, SNACK, excludeTags);

        List<DayPlanRecipe> dayPlanRecipes = new ArrayList<>(breakfastRecipes);
        dayPlanRecipes.addAll(lunchRecipes);
        dayPlanRecipes.addAll(dinnerRecipes);
        dayPlanRecipes.addAll(snackRecipes);

        dayPlan.setRecipes(dayPlanRecipes);
    }

    //TODO: refactor... call for each type of meal (breakfast, lunch, dinner, etc. according to the calorie intake)
    private List<DayPlanRecipe> getSpecificRecipesForMeal(DayPlan dayPlan, Double totalCalories, MealTimeEnum mealTime, String excludeTags) {
        List<Recipe> candidates = recipeRepository.findAllByMealTimeAndTagsNotContainingIgnoreCase(mealTime, excludeTags);
        log.info("Candidates: {}", candidates);
        Collections.shuffle(candidates);

        double target = getCaloriesPerMealTime(totalCalories, mealTime);
        List<Recipe> recipes = new ArrayList<>();
        int total = 0;

        for (Recipe r : candidates) {
            if (total + r.getTotalCalories() <= target + 100) {
                recipes.add(r);
                total += r.getTotalCalories();
            }
        }

        log.info("Recipes: {}.", recipes);
        List<DayPlanRecipe> dayPlanRecipes = recipes.stream().map(recipe -> DayPlanRecipe.builder().recipe(recipe).mealTime(mealTime).dayPlan(dayPlan).build()).toList();
        log.info("DayPlanRecipes: {}", dayPlanRecipes);

        return dayPlanRecipeRepository.saveAllAndFlush(dayPlanRecipes);
    }

    private double getCaloriesPerMealTime(Double totalCalories, MealTimeEnum mealTime) {
        int intake = mealTime.getIntake();

        // intake represents the proportion of the total kcal per day for a specific meal, ex. breakfast is 35% of daily kcal intake.
        return totalCalories * intake / 100;
    }
}
