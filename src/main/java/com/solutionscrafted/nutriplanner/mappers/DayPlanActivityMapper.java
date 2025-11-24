package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanActivityDto;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SportActivityMapper.class})
public interface DayPlanActivityMapper {

    @Mapping(source = "id.dayPlanId", target = "dayPlanId")
    @Mapping(source = "id.activityId", target = "activityId")
    @Mapping(source = "activity", target = "activity")
    DayPlanActivityDto toDto(DayPlanActivity entity);

    List<DayPlanActivityDto> toDtoList(List<DayPlanActivity> entities);
}
