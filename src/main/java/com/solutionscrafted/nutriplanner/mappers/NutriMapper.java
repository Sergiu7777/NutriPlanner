package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.ClientDto;
import com.solutionscrafted.nutriplanner.dto.FoodDto;
import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import com.solutionscrafted.nutriplanner.entity.Client;
import com.solutionscrafted.nutriplanner.entity.Food;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NutriMapper {

  //Client mappers
  ClientDto toDto(Client client);
  Client toClient(ClientDto clientDto);
  List<ClientDto> toClientDtoList(List<Client> clients);

  //Food mappers
  FoodDto toDto(Food food);
  Food toFood(FoodDto foodDto);
  List<FoodDto> toFoodDtoList(List<Food> foods);

  //Recipe mappers
  RecipeDto toDto(Recipe recipe);
  Recipe toRecipe(RecipeDto recipeDto);
  List<RecipeDto> toRecipeDtoList(List<Recipe> recipes);

  //Plan mappers
  PlanDto toDto(Plan plan);
  Plan toPlan(PlanDto planDto);
  List<PlanDto> toPlanDtoList(List<Plan> plans);
}
