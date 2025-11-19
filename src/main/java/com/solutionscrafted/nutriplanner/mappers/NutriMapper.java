package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.*;
import com.solutionscrafted.nutriplanner.entity.*;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NutriMapper {

  // Client mappers
  //  @Mapping(target = "plans", qualifiedByName = "toPlanDtoList")
  ClientDto toDto(Client client);

  Client toClient(ClientDto clientDto);

  List<ClientDto> toClientDtoList(List<Client> clients);

  // Food mappers
  FoodDto toDto(Food food);

  Food toFood(FoodDto foodDto);

  List<FoodDto> toFoodDtoList(List<Food> foods);

  // Recipe mappers
//  @Mapping(target = "ingredients", ignore = true)
  RecipeDto toDto(Recipe recipe);

  Recipe toRecipe(RecipeDto recipeDto);

  List<RecipeDto> toRecipeDtoList(List<Recipe> recipes);

  // Plan mappers
  PlanDto toDto(Plan plan);

  Plan toPlan(PlanDto planDto);

  //  @Named("toPlanDtoList")
  List<PlanDto> toPlanDtoList(List<Plan> plans);

//  @Mapping(target = "recipeId", expression = "ingredientRecipe.recipeId")
//  @Mapping(target = "foodId", expression = "ingredientRecipe.foodId")
//  IngredientRecipeDto toIngredientsRecipeDto(IngredientRecipe ingredientRecipe);


//  List<IngredientRecipeDto> toIngredientsRecipeDtoList(List<IngredientRecipe> ingredientRecipes);
}
