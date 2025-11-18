package com.solutionscrafted.nutriplanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plan_recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanRecipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_plan", nullable = false)
  private Plan plan;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_recipe", nullable = false)
  private Recipe recipe;

  @Column(nullable = false)
  private Integer day;

  @Column(nullable = false)
  private String mealTime;
}
