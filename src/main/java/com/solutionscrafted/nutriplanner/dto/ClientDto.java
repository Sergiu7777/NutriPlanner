package com.solutionscrafted.nutriplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ClientDto(
        Long id,
        @NotBlank(message = "Client name is required") String name,
        @NotNull(message = "Age is required")
        @PositiveOrZero(message = "Age must be integer, non-negative value")
        Integer age,
        @NotNull(message = "Weight is required")
        @Positive(message = "Weight must be a non-negative value")
        Double weight,
        @NotNull(message = "Height is required")
        @Positive(message = "Height must be a non-negative value")
        Integer height,
        @NotBlank(message = "Goal is required") String goal,
        @NotNull(message = "Daily calorie intake is required") Double dailyCalories) {
}
