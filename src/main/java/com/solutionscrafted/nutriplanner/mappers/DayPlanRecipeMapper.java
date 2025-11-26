package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanRecipeDto;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanRecipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RecipeMapper.class})
public interface DayPlanRecipeMapper {

    @Mapping(source = "id.dayPlanId", target = "dayPlanId")
    @Mapping(source = "id.recipeId", target = "recipeId")
    DayPlanRecipeDto toDto(DayPlanRecipe entity);

    List<DayPlanRecipeDto> toDtoSet(List<DayPlanRecipe> entities);
}
