package com.solutionscrafted.nutriplanner.dto;

import com.solutionscrafted.nutriplanner.entity.IngredientRecipe;
import java.util.List;

public record RecipeDto(
    Long id,
    String name,
    String instructions,
    Double totalCalories,
    String tags,
    List<IngredientRecipe> ingredients) {}
