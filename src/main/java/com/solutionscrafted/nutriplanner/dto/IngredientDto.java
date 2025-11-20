package com.solutionscrafted.nutriplanner.dto;


public record IngredientDto(
        Long id,
        String name,
        double calories,
        double protein,
        double carbs,
        double fat,
        String category,
        String tags) {
}
