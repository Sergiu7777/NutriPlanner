package com.solutionscrafted.nutriplanner.dto.dayplan;

import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum;

public record DayPlanRecipeDto(
        Long dayPlanId,
        Long recipeId,
        MealTimeEnum mealTime,
        RecipeDto recipe
) {
}

