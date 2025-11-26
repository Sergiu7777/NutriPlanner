package com.solutionscrafted.nutriplanner.controller;

import com.solutionscrafted.nutriplanner.controller.requestbody.DayPlanRequestDto;
import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanDto;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import com.solutionscrafted.nutriplanner.service.DayPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class DayPlanController {
    private final DayPlanService dayPlanService;

    @PostMapping("/{plan_id}/days/generate")
    public ResponseEntity<List<DayPlan>> generateDayPlans(@PathVariable("plan_id") Long planId, @RequestParam(defaultValue = "7") int days) {
        log.info("Request generate plan days: /plans/day/{}/generate.", days);

        return ResponseEntity.ok(dayPlanService.generateDayPlans(planId, days));
    }

    @GetMapping("/{plan_id}/days")
    public ResponseEntity<Set<DayPlanDto>> getPlanDays(@PathVariable("plan_id") Long planId) {
        log.info("Request get days from plan: /plans/day/{}.", planId);

        return ResponseEntity.ok(dayPlanService.getDayPlansByPlan(planId));
    }

    @PostMapping("/{plan_id}/days/{dp_id}/recipe/{recipe_id}")
    public ResponseEntity<Void> addRecipe(@PathVariable("plan_id") Long planId, @PathVariable("dp_id") Long dayPlanId, @PathVariable("recipe_id") Long recipeId) {
        log.info("Request add recipe for a specific day plan: /plans/day/{}/recipe/{}.", dayPlanId, recipeId);

        dayPlanService.addRecipeToDay(dayPlanId, recipeId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{plan_id}/days/{dp_id}/activity/{activity_id}")
    public ResponseEntity<Void> addActivity(@PathVariable("plan_id") Long planId, @PathVariable("dp_id") Long dayPlanId, @PathVariable("activity_id") Long activityId) {
        log.info("Request add activity for day in plan: /plans/day/{}/activity/{}.", dayPlanId, activityId);

        dayPlanService.addActivityToDay(dayPlanId, activityId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{plan_id}/days/{dp_id}")
    public ResponseEntity<Void> deleteDayPlan(@PathVariable("plan_id") Long planId, @PathVariable("dp_id") Long dayPlanId) {
        log.info("Request delete day with id: {} from plan with id: {}.", planId, dayPlanId);

        dayPlanService.deleteDayPlan(dayPlanId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{plan_id}/days/{dp_id}/note")
    public ResponseEntity<DayPlanDto> updateDPNote(@PathVariable("plan_id") Long planId, @PathVariable("dp_id") Long dayPlanId, @RequestBody DayPlanRequestDto requestDto) {
        log.info("Request update day plan note: /plans/{}/days/{}/note.", planId, dayPlanId);

        DayPlanDto dpUpdated = dayPlanService.addOrUpdateDPNote(requestDto);

        return ResponseEntity.ok(dpUpdated);
    }
}

