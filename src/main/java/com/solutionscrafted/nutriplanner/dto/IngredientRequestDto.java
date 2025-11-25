package com.solutionscrafted.nutriplanner.dto;

public record IngredientRequestDto(
        String name,
        double calories,
        double protein,
        double carbs,
        double fat,
        String category,
        String tags
) {
}
