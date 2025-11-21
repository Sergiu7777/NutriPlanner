package com.solutionscrafted.nutriplanner.dto.dayplan;

import lombok.Builder;
import java.util.List;

@Builder
public record DayPlanDto(
        Long id,
        Integer day,
        String note,
        Long planId,
        List<DayPlanRecipeDto> recipes,
        List<DayPlanActivityDto> activities
) {}
