package com.solutionscrafted.nutriplanner.dto.dayplan;

import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlanRecipeDto {

    private Long dayPlanId;
    private Long recipeId;
    private String mealTime;

    private RecipeDto recipe; // optional: include recipe details
}

