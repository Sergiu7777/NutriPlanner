package com.solutionscrafted.nutriplanner.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ClientDto(
    Long id,
    @NotBlank(message = "Client name is required") String name,
    @NotNull(message = "Age is required")
        @PositiveOrZero(message = "Age must be integer, non-negative value")
        Integer age,
    @NotNull(message = "Weight is required") @Positive(message = "Age must be a non-negative value")
        Double weight,
    Double height,
    String goal,
    Double dailyCalories) {}
