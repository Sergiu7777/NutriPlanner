package com.solutionscrafted.nutriplanner.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String instructions;

  @Column(nullable = false)
  private Double totalCalories;

  private String tags;

  private MealTimeEnum mealTime;

  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<IngredientRecipe> ingredients;
}
