package com.solutionscrafted.nutriplanner.repository.dayplan;

import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanRecipe;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanRecipeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayPlanRecipeRepository extends JpaRepository<DayPlanRecipe, DayPlanRecipeId> {
}

