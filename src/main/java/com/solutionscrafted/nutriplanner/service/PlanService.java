package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.controller.requestbody.PlanRequestDto;
import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.entity.Client;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import com.solutionscrafted.nutriplanner.entity.SportActivity;
import com.solutionscrafted.nutriplanner.entity.dayplan.*;
import com.solutionscrafted.nutriplanner.mappers.PlanMapper;
import com.solutionscrafted.nutriplanner.repository.ActivityRepository;
import com.solutionscrafted.nutriplanner.repository.ClientRepository;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {

    private final ActivityRepository activityRepository;
    private final PlanRepository planRepository;
    private final RecipeRepository recipeRepository;
    private final ClientRepository clientRepository;
    private final DayPlanRepository dayPlanRepository;
    private final DayPlanRecipeRepository dayPlanRecipeRepository;
    private final PlanMapper planMapper;

    public List<PlanDto> getAllPlans() {
        return planMapper.toPlanDtoList(planRepository.findAll());
    }

    public List<PlanDto> getPlansByClientId(Long id) {
        return planMapper.toPlanDtoList(planRepository.findByClient_Id(id));
    }

    public PlanDto updatePlan(Long id, PlanRequestDto requestDto) {
        Plan plan = planRepository.findById(id).orElseThrow(() -> new RuntimeException("Plan not found for id: " + id));

        plan.setTitle(requestDto.title());
        plan.setDescription(requestDto.description());
        plan.setTotalCalories(requestDto.totalCalories());
        plan.setNumberOfDays(requestDto.numberOfDays());

        Plan updated = planRepository.save(plan);
        return planMapper.toPlanDto(updated);
    }

    public void deletePlan(Long id) {
        log.info("Deleting plan for planId: {}.", id);

        planRepository.findById(id).ifPresentOrElse(planRepository::delete, () -> {
            log.warn("Plan not found with id: {}", id);
        });
    }

    public PlanDto generatePlan(PlanRequestDto requestDto) {
        Client client = clientRepository.findById(requestDto.clientId().longValue()).orElseThrow(() -> new RuntimeException("Client not found!"));
        double totalCalories = requestDto.totalCalories();

        Plan plan = planMapper.toPlan(requestDto);
        plan.setClient(client);

        Plan planSaved = planRepository.save(plan);

        // Calculate activity for each day;
        // Observation notes for each day;
        // DayPlan for each day, splitting the total kcal between breakfast, lunch dinner snack.
        // Split kcal intake 30% breakfast, 40% lunch, 25% dinner, 5% snack
        // Shuffle the recipes for each type of meal.
        // Soup should not be available for breakfast!
        List<DayPlan> dayPlanList = new ArrayList<>();
        Integer days = requestDto.numberOfDays();

        for (int i = 1; i <= days; i++) {
            DayPlan dayPlan = new DayPlan();
            dayPlan.setDay(i);
            dayPlan.setPlan(plan);

            DayPlan dayPlanSaved = dayPlanRepository.save(dayPlan);
            log.info("DayPlanSaved: {}.", dayPlanSaved);

            assignDailyActivities(dayPlanSaved);
            assignDailyRecipes(dayPlanSaved, totalCalories, requestDto.excludeTag());

            double total = calculateTotalCaloriesForDay(dayPlan);
            dayPlan.setTotalCalories(total);

            dayPlanList.add(dayPlanSaved);
        }

        planSaved.setDayPlans(dayPlanList);
        log.info("Generated plan: {}", plan);

        Plan planReady = planRepository.save(planSaved);
        return planMapper.toPlanDto(planReady);
    }

    private void assignDailyActivities(DayPlan dayPlan) {
        List<SportActivity> allActivities = activityRepository.findAll();
        int numberOfActivities = 1; // change to 2–3 if needed

        Collections.shuffle(allActivities);

        dayPlan.getActivities().clear();

        for (int i = 0; i < numberOfActivities; i++) {
            SportActivity selected = allActivities.get(i);
            DayPlanActivityId id = new DayPlanActivityId(dayPlan.getId(), selected.getId());
            DayPlanActivity dpa = new DayPlanActivity(id, dayPlan, selected);

            dayPlan.getActivities().add(dpa);
        }

        dayPlanRepository.save(dayPlan);
    }

    // here will create recipes for a whole day
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

        log.info("DayPlanRecipes: {}", dayPlanRecipes);

        adjustServingsToHitTarget(dayPlanRecipes, target);

        log.info("Total daily calories: {}, Requested daily intake: {}.", dayPlanRecipes.stream().mapToDouble(dpr -> dpr.getRecipe().getTotalCalories()).sum(), target);

        dayPlanRecipes.forEach(dpr -> {
            dpr.setDayPlan(dayPlan);
            dayPlan.getRecipes().add(dpr);
        });
    }

    private List<DayPlanRecipe> getSpecificRecipesForMeal(DayPlan dayPlan, Double totalCalories, MealTimeEnum mealTime, String excludeTags) {
        List<Recipe> candidates = recipeRepository.findAllByMealTimeAndTagsNotContainingIgnoreCase(mealTime, excludeTags);
        log.info("Candidates: {}", candidates);

        Collections.shuffle(candidates);

        double target = getCaloriesPerMealTime(totalCalories, mealTime);
        log.info("Meal: {}, Intake: {}%, TargetIntake: {}, Total: {}", mealTime.getMeal(), mealTime.getIntake(), target, totalCalories);

        List<Recipe> recipes = new ArrayList<>();
        int total = 0;

        for (Recipe r : candidates) {
            if (total + r.getTotalCalories() <= target + 50) {
                recipes.add(r);
                total += r.getTotalCalories();
            }
        }

        List<DayPlanRecipe> dayPlanRecipes = recipes.stream()
                .map(recipe -> DayPlanRecipe.builder()
                        .id(new DayPlanRecipeId(dayPlan.getId(), recipe.getId()))
                        .recipe(recipe)
                        .mealTime(mealTime)
                        .dayPlan(dayPlan)
                        .servings(1.0)
                        .build())
                .toList();

        return dayPlanRecipeRepository.saveAll(dayPlanRecipes);
    }

    private double getCaloriesPerMealTime(Double totalCalories, MealTimeEnum mealTime) {
        int intake = mealTime.getIntake();

        // intake represents the proportion of the total kcal per day for a specific meal, ex. breakfast is 35% of daily kcal intake.
        return totalCalories * intake / 100;
    }

    private void adjustServingsToHitTarget(List<DayPlanRecipe> dayPlanRecipes, double targetCalories) {
        double currentTotal = dayPlanRecipes.stream()
                .mapToDouble(dpr -> dpr.getRecipe().getTotalCalories())
                .sum();

        if (currentTotal == 0) {
            return; // nothing to adjust
        }

        // scale servings — default is assumed 1.0
        double scale = Math.round(targetCalories / currentTotal * 10.0) / 10.0;
        dayPlanRecipes.forEach(dpr -> dpr.setServings(scale));
    }

    private double calculateTotalCaloriesForDay(DayPlan dayPlan) {
        return dayPlan.getRecipes().stream()
                .mapToDouble(dpr -> {
                    Recipe recipe = dpr.getRecipe();
                    double servings = dpr.getServings() != null ? dpr.getServings() : 1.0;
                    return recipe.getTotalCalories() * servings;
                })
                .sum();
    }

}
