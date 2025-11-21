package com.solutionscrafted.nutriplanner.dto;

public record PlanRequestDto(
        String title,
        String description,
        Integer totalCalories,
        Integer numberOfDays,
        Integer clientId,
        String excludeTag) {
}
