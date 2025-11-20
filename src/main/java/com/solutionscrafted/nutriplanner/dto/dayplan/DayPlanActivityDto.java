package com.solutionscrafted.nutriplanner.dto.dayplan;

import com.solutionscrafted.nutriplanner.dto.SportActivityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlanActivityDto {

    private Long dayPlanId;
    private Long activityId;

    private SportActivityDto activity; // optional
}
