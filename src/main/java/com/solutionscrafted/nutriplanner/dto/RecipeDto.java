package com.solutionscrafted.nutriplanner.dto;

import lombok.Builder;

@Builder
public record RecipeDto(
        Long id,
        String name,
        String instructions,
        Integer totalCalories,
        String tags
) {
}
