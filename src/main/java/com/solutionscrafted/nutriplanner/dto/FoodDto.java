package com.solutionscrafted.nutriplanner.dto;


public record FoodDto(
    Long id,
    String name,
    double calories,
    double protein,
    double carbs,
    double fat,
    String category,
    String tags) {}
