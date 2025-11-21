package com.solutionscrafted.nutriplanner.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlanDto(
        Long id,
        String title,
        String description,
        Double totalCalories,
        Integer numberOfDays,
        Integer clientId,
        String excludeTag,
        LocalDateTime dateCreated) {
}
