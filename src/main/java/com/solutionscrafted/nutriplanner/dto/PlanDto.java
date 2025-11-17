package com.solutionscrafted.nutriplanner.dto;

import com.solutionscrafted.nutriplanner.entity.PlanRecipe;

import java.time.LocalDateTime;
import java.util.List;

public record PlanDto(
    Long id,
    Long clientId,
    LocalDateTime dateCreated,
    Double totalCalories,
    List<PlanRecipe> planRecipes) {}
