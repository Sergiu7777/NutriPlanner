package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanDto;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        DayPlanRecipeMapper.class,
        DayPlanActivityMapper.class
})
public interface DayPlanMapper {

    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "recipes", target = "recipes")
    @Mapping(source = "activities", target = "activities")
    DayPlanDto toDto(DayPlan dayPlan);

    List<DayPlanDto> toDtoList(List<DayPlan> dayPlans);

    @Mapping(source = "planId", target = "plan.id")
    DayPlan toEntity(DayPlanDto dto);
}
