package com.solutionscrafted.nutriplanner.dto;

import java.time.LocalDateTime;

public record PlanDto(Long id, String title, Integer totalCalories, String description, Integer numberOfDays,
                      LocalDateTime dateCreated) {
}
