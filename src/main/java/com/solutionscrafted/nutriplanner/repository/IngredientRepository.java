package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

