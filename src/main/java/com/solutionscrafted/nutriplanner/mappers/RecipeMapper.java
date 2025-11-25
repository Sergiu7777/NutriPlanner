package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.controller.requestbody.RecipeRequestDto;
import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDto toRecipeDto(Recipe recipe);

    Recipe toRecipe(RecipeRequestDto requestDto);

    Recipe toRecipe(RecipeDto recipeDto);

    List<RecipeDto> toRecipeDtoList(List<Recipe> recipes);
}
