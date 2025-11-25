package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum;
import com.solutionscrafted.nutriplanner.service.DayPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/plans/day")
@RequiredArgsConstructor
public class DayPlanController {
    private final DayPlanService dayPlanService;

    @PostMapping("/{planId}/generate")
    public ResponseEntity<List<DayPlan>> generateDayPlans(@PathVariable Long planId, @RequestParam(defaultValue = "7") int days) {
        log.info("Generate plan days request: /plans/day/{}/generate.", days);

        return ResponseEntity.ok(dayPlanService.generateDayPlans(planId, days));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<List<DayPlan>> getPlanDays(@PathVariable Long planId) {
        log.info("Get days from plan request: /plans/day/{}.", planId);

        return ResponseEntity.ok(dayPlanService.getDayPlansByPlan(planId));
    }

    @PostMapping("/{dayPlanId}/recipe/{recipeId}")
    public ResponseEntity<Void> addRecipe(@PathVariable Long dayPlanId, @PathVariable Long recipeId, @RequestParam MealTimeEnum mealTime) {
        log.info("Add recipe for a specific day plan: /plans/day/{}/recipe/{}.", dayPlanId, recipeId);

        dayPlanService.addRecipeToDay(dayPlanId, recipeId, mealTime);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{dayPlanId}/activity/{activityId}")
    public ResponseEntity<Void> addActivity(@PathVariable Long dayPlanId, @PathVariable Long activityId) {
        log.info("Add activity for day in plan: /plans/day/{}/activity/{}.", dayPlanId, activityId);

        dayPlanService.addActivityToDay(dayPlanId, activityId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{dayPlanId}")
    public ResponseEntity<Void> deleteDayPlan(@PathVariable Long dayPlanId) {
        log.info("Delete day plan: /plans/day/{}.", dayPlanId);

        dayPlanService.deleteDayPlan(dayPlanId);
        return ResponseEntity.noContent().build();
    }
}

