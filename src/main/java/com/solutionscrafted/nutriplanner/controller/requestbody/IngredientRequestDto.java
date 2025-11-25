package com.solutionscrafted.nutriplanner.controller.requestbody;

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
