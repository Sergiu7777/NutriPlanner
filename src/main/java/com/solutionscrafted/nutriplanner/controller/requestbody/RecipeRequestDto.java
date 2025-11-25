package com.solutionscrafted.nutriplanner.controller.requestbody;

import com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum;
import lombok.Builder;

@Builder
public record RecipeRequestDto(
        String name,
        String instructions,
        Integer totalCalories,
        String tags,
        MealTimeEnum mealTime
) {
}
