package com.solutionscrafted.nutriplanner.dto.dayplan;

import lombok.Builder;

import java.util.List;

@Builder
public record DayPlanDto(
        Long id,
        Integer day,
        String note,
        Long planId,
        Double totalCalories,
        List<DayPlanRecipeDto> recipes,
        List<DayPlanActivityDto> activities
) {
}
