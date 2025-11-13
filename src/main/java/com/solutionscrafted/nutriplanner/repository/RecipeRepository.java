package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
