package com.solutionscrafted.nutriplanner.dto.dayplan;

import com.solutionscrafted.nutriplanner.dto.SportActivityDto;
import lombok.Builder;

@Builder
public record DayPlanActivityDto(
        Long dayPlanId,
        Long activityId,
        SportActivityDto activity
) {
}

