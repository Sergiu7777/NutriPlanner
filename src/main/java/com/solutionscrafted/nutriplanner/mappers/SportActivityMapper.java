package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.SportActivityDto;
import com.solutionscrafted.nutriplanner.entity.SportActivity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SportActivityMapper {

    SportActivityDto toDto(SportActivity entity);

    List<SportActivityDto> toDtoList(List<SportActivity> entities);
}
