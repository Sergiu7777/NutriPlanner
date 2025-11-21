package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.Recipe;
import com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByMealTimeAndTagsNotContainingIgnoreCase(MealTimeEnum meal, String tags);

    List<Recipe> findAllByMealTime(MealTimeEnum mealTime);

    List<Recipe> findAllByTagsContainingIgnoreCase(String tag);
}
