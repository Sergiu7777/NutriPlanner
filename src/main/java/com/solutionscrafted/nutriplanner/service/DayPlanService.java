package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.controller.requestbody.DayPlanRequestDto;
import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanDto;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import com.solutionscrafted.nutriplanner.entity.SportActivity;
import com.solutionscrafted.nutriplanner.entity.dayplan.*;
import com.solutionscrafted.nutriplanner.mappers.DayPlanMapper;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanActivityRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayPlanService {

    private final DayPlanRepository dayPlanRepository;
    private final PlanRepository planRepository;
    private final RecipeRepository recipeRepository;
    private final DayPlanRecipeRepository dayPlanRecipeRepository;
    private final DayPlanActivityRepository dayPlanActivityRepository;
    private final DayPlanMapper dpMapper;

    /**
     * Generate a full day plan structure (7-day or 14-day plan).
     */
    @Transactional
    public List<DayPlan> generateDayPlans(Long planId, int days) {

        Plan plan = planRepository.findById(planId).orElseThrow(() -> new RuntimeException("Plan not found: " + planId));

        List<DayPlan> dayPlans = new ArrayList<>();

        for (int day = 1; day <= days; day++) {
            DayPlan dayPlan = new DayPlan();
            dayPlan.setDay(day);
            dayPlan.setPlan(plan);

            dayPlans.add(dayPlan);
        }

        return dayPlanRepository.saveAll(dayPlans);
    }

    /**
     * Add a recipe to a specific day and meal time.
     */
    @Transactional
    public void addRecipeToDay(Long dayPlanId, Long recipeId) {

        DayPlan dayPlan = dayPlanRepository.findById(dayPlanId).orElseThrow(() -> new RuntimeException("DayPlan not found: " + dayPlanId));

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));

        DayPlanRecipeId id = new DayPlanRecipeId(dayPlanId, recipeId);

        DayPlanRecipe dpr = DayPlanRecipe.builder().id(id).dayPlan(dayPlan).recipe(recipe).mealTime(recipe.getMealTime()).build();

        dayPlanRecipeRepository.save(dpr);
    }

    /**
     * Add activity to a day.
     */
    @Transactional
    public void addActivityToDay(Long dayPlanId, Long activityId) {

        DayPlan dayPlan = dayPlanRepository.findById(dayPlanId).orElseThrow(() -> new RuntimeException("DayPlan not found"));

        SportActivity activity = new SportActivity();
        activity.setId(activityId);

        DayPlanActivityId id = new DayPlanActivityId(dayPlanId, activityId);
        DayPlanActivity dpa = new DayPlanActivity(id, dayPlan, activity);

        dayPlanActivityRepository.save(dpa);
    }

    /**
     * Get all day plans for a plan.
     */
    @Transactional(readOnly = true)
    public Set<DayPlanDto> getDayPlansByPlan(Long planId) {
        return dpMapper.toDtoSet(dayPlanRepository.findByPlanId(planId));
    }

    /**
     * Delete a specific day plan.
     */
    @Transactional
    public void deleteDayPlan(Long dayPlanId) {
        dayPlanRepository.deleteById(dayPlanId);
    }

    public DayPlanDto addOrUpdateDPNote(DayPlanRequestDto requestDto) {
        DayPlan dayPlan = dayPlanRepository.findById(requestDto.id())
                .orElseThrow(() -> new RuntimeException(String.format("DayPlan not found for id: %s", requestDto.id())));

        if (!requestDto.note().equals(dayPlan.getNote())) {
            dayPlan.setNote(requestDto.note());

            return dpMapper.toDto(dayPlanRepository.save(dayPlan));
        }

        return dpMapper.toDto(dayPlan);
    }
}

