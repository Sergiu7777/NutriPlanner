package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.ClientDto;
import com.solutionscrafted.nutriplanner.dto.IngredientDto;
import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.dto.RecipeDto;
import com.solutionscrafted.nutriplanner.entity.Client;
import com.solutionscrafted.nutriplanner.entity.Ingredient;
import com.solutionscrafted.nutriplanner.entity.Plan;
import com.solutionscrafted.nutriplanner.entity.Recipe;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DayPlanMapper.class})
public interface NutriMapper { //TODO: refactor mapper, cleanup

    // Client mappers
    //  @Mapping(target = "plans", qualifiedByName = "toPlanDtoList")
    ClientDto toDto(Client client);

    Client toClient(ClientDto clientDto);

    List<ClientDto> toClientDtoList(List<Client> clients);

    // Ingredient mappers
    IngredientDto toIngredientDto(Ingredient ingredient);

    Ingredient toIngredient(IngredientDto ingredientDto);

    List<IngredientDto> toIngredientDtoList(List<Ingredient> ingredients);

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
