package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.IngredientDto;
import com.solutionscrafted.nutriplanner.entity.Ingredient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientDto toIngredientDto(Ingredient ingredient);

    Ingredient toIngredient(IngredientDto ingredientDto);

    List<IngredientDto> toIngredientDtoList(List<Ingredient> ingredients);
}
