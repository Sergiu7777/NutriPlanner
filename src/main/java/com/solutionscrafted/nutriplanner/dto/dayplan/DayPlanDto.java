package com.solutionscrafted.nutriplanner.dto.dayplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlanDto {

    private Long id;
    private Integer day;
    private Long planId;

    private List<DayPlanRecipeDto> recipes;
    private List<DayPlanActivityDto> activities;
    private List<DayPlanNoteDto> notes;
}

