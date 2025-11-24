package com.solutionscrafted.nutriplanner.dto;

import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanDto;

import java.time.LocalDateTime;
import java.util.List;

public record PlanDto(
        Long id,
        String title,
        String description,
        Double totalCalories,
        Integer numberOfDays,
        Integer clientId,
        LocalDateTime dateCreated,
        List<DayPlanDto> dayPlanDtoList) {
}
