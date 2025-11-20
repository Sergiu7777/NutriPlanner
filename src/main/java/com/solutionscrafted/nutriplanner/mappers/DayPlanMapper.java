package com.solutionscrafted.nutriplanner.mappers;

import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanActivityDto;
import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanDto;
import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanNoteDto;
import com.solutionscrafted.nutriplanner.dto.dayplan.DayPlanRecipeDto;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanActivity;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanNote;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanRecipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DayPlanMapper {

    // -----------------------------
    // DayPlan
    // -----------------------------
    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "recipes", target = "recipes")
    @Mapping(source = "activities", target = "activities")
    @Mapping(source = "notes", target = "notes")
    DayPlanDto toDto(DayPlan entity);

    @Mapping(source = "planId", target = "plan.id")
    DayPlan toEntity(DayPlanDto dto);

    List<DayPlanDto> toDtoList(List<DayPlan> entities);


    // -----------------------------
    // DayPlanRecipe
    // -----------------------------
    @Mapping(source = "id.dayPlanId", target = "dayPlanId")
    @Mapping(source = "id.recipeId", target = "recipeId")
    @Mapping(source = "mealTime", target = "mealTime")
    @Mapping(source = "recipe", target = "recipe")
    DayPlanRecipeDto toDto(DayPlanRecipe entity);

    @Mapping(target = "id.dayPlanId", source = "dayPlanId")
    @Mapping(target = "id.recipeId", source = "recipeId")
    @Mapping(source = "mealTime", target = "mealTime")
    @Mapping(target = "dayPlan", ignore = true) // set in service
    @Mapping(target = "recipe", ignore = true)
        // set in service
    DayPlanRecipe toEntity(DayPlanRecipeDto dto);


    // -----------------------------
    // DayPlanActivity
    // -----------------------------
    @Mapping(source = "id.dayPlanId", target = "dayPlanId")
    @Mapping(source = "id.activityId", target = "activityId")
    @Mapping(source = "activity", target = "activity")
    DayPlanActivityDto toDto(DayPlanActivity entity);

    @Mapping(target = "id.dayPlanId", source = "dayPlanId")
    @Mapping(target = "id.activityId", source = "activityId")
    @Mapping(target = "dayPlan", ignore = true)    // set in service
    @Mapping(target = "activity", ignore = true)
        // set in service
    DayPlanActivity toEntity(DayPlanActivityDto dto);


    // -----------------------------
    // DayPlanNote
    // -----------------------------
    @Mapping(source = "id.dayPlanId", target = "dayPlanId")
    @Mapping(source = "id.noteId", target = "noteId")
    @Mapping(source = "note", target = "note")
    DayPlanNoteDto toDto(DayPlanNote entity);

    @Mapping(target = "id.dayPlanId", source = "dayPlanId")
    @Mapping(target = "id.noteId", source = "noteId")
    @Mapping(target = "dayPlan", ignore = true)   // set in service
    @Mapping(target = "note", ignore = true)
        // set in service
    DayPlanNote toEntity(DayPlanNoteDto dto);
}

