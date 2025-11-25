package com.solutionscrafted.nutriplanner.controller.requestbody;

public record PlanRequestDto(
        String title,
        String description,
        Double totalCalories,
        Integer numberOfDays,
        Integer clientId,
        String excludeTag) {
}
