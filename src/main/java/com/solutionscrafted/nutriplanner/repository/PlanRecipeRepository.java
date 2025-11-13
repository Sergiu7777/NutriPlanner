package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.PlanRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRecipeRepository extends JpaRepository<PlanRecipe, Long> {
}
