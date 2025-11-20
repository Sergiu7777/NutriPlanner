package com.solutionscrafted.nutriplanner.service;

import com.solutionscrafted.nutriplanner.entity.Note;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import com.solutionscrafted.nutriplanner.entity.SportActivity;
import com.solutionscrafted.nutriplanner.entity.dayplan.*;
import com.solutionscrafted.nutriplanner.repository.PlanRepository;
import com.solutionscrafted.nutriplanner.repository.RecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanActivityRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanNoteRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRecipeRepository;
import com.solutionscrafted.nutriplanner.repository.dayplan.DayPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayPlanService {

    private final DayPlanRepository dayPlanRepository;
    private final PlanRepository planRepository;
    private final RecipeRepository recipeRepository;
    private final DayPlanRecipeRepository dayPlanRecipeRepository;
    private final DayPlanActivityRepository dayPlanActivityRepository;
    private final DayPlanNoteRepository dayPlanNoteRepository;

    /**
     * Generate a full day plan structure (7-day or 14-day plan).
     */
    @Transactional
    public List<DayPlan> generateDayPlans(Long planId, int days) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found: " + planId));

        List<DayPlan> dayPlans = new ArrayList<>();

        for (int day = 1; day <= days; day++) {
            DayPlan dp = DayPlan.builder()
                    .plan(plan)
                    .day(day)
                    .build();

            dayPlans.add(dp);
        }

        return dayPlanRepository.saveAll(dayPlans);
    }

    /**
     * Add a recipe to a specific day and meal time.
     */
    @Transactional
    public void addRecipeToDay(Long dayPlanId, Long recipeId, MealTimeEnum mealTime) {

        DayPlan dayPlan = dayPlanRepository.findById(dayPlanId)
                .orElseThrow(() -> new RuntimeException("DayPlan not found: " + dayPlanId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));

        DayPlanRecipeId id = new DayPlanRecipeId(dayPlanId, recipeId);

        DayPlanRecipe dpr = DayPlanRecipe.builder()
                .id(id)
                .dayPlan(dayPlan)
                .recipe(recipe)
                .mealTime(mealTime)
                .build();

        dayPlanRecipeRepository.save(dpr);
    }

    /**
     * Add activity to a day.
     */
    @Transactional
    public void addActivityToDay(Long dayPlanId, Long activityId) {

        DayPlan dayPlan = dayPlanRepository.findById(dayPlanId)
                .orElseThrow(() -> new RuntimeException("DayPlan not found"));

        SportActivity activity = new SportActivity();
        activity.setId(activityId);

        DayPlanActivityId id = new DayPlanActivityId(dayPlanId, activityId);

        DayPlanActivity dpa = DayPlanActivity.builder()
                .id(id)
                .dayPlan(dayPlan)
                .activity(activity)
                .build();

        dayPlanActivityRepository.save(dpa);
    }

    /**
     * Add note to day.
     */
    @Transactional
    public void addNoteToDay(Long dayPlanId, Long noteId) {

        DayPlan dayPlan = dayPlanRepository.findById(dayPlanId)
                .orElseThrow(() -> new RuntimeException("DayPlan not found"));

        Note note = new Note();
        note.setId(noteId);

        DayPlanNoteId id = new DayPlanNoteId(dayPlanId, noteId);

        DayPlanNote dpn = DayPlanNote.builder()
                .id(id)
                .dayPlan(dayPlan)
                .note(note)
                .build();

        dayPlanNoteRepository.save(dpn);
    }

    /**
     * Get all day plans for a plan.
     */
    @Transactional(readOnly = true)
    public List<DayPlan> getDayPlansByPlan(Long planId) {
        return dayPlanRepository.findByPlanId(planId);
    }

    /**
     * Delete a specific day plan.
     */
    @Transactional
    public void deleteDayPlan(Long dayPlanId) {
        dayPlanRepository.deleteById(dayPlanId);
    }
}

