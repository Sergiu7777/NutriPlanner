package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.PlanDto;
import com.solutionscrafted.nutriplanner.dto.PlanRequestDto;
import com.solutionscrafted.nutriplanner.entity.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DayPlanMapper.class})
public interface PlanMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "dayPlans", target = "dayPlanDtoList")
    PlanDto toPlanDto(Plan plan);

    Plan toPlan(PlanRequestDto requestDto);

    List<PlanDto> toPlanDtoList(List<Plan> plans);
}
