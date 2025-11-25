package com.solutionscrafted.nutriplanner.entity.dayplan;

import com.solutionscrafted.nutriplanner.entity.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "day_plan_recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlanRecipe {

    @EmbeddedId
    private DayPlanRecipeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dayPlanId")
    @JoinColumn(name = "day_plan_id")
    private DayPlan dayPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_time", nullable = false)
    private MealTimeEnum mealTime;

    private Double servings;
}
