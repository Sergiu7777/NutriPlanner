package com.solutionscrafted.nutriplanner.controller.requestbody;

public record DayPlanRequestDto(
        Long id,
        Long planId,
        String note
) {
}
