package com.solutionscrafted.nutriplanner.entity;

import com.solutionscrafted.nutriplanner.entity.dayplan.MealTimeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "recipes")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String instructions;

    @Column(nullable = false)
    private Integer totalCalories; //TODO: calculate calories

    private String tags;

    // Shuffle the recipes for each type of meal.
    // Soup should not be available for breakfast!
    // Split kcal intake 30% breakfast, 40% lunch, 25% dinner, 5% snack
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_time", nullable = false)
    private MealTimeEnum mealTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientRecipe> ingredients;
}
