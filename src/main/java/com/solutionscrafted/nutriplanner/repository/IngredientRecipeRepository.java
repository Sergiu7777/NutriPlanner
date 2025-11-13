package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.IngredientRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRecipeRepository extends JpaRepository<IngredientRecipe, Long> {
}
