package com.solutionscrafted.nutriplanner.dto;

import jakarta.validation.constraints.*;
import java.util.List;
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
        Double height,
    @NotBlank(message = "Goal is required") String goal,
    @NotNull(message = "Daily calorie intake is required") Double dailyCalories) {}
