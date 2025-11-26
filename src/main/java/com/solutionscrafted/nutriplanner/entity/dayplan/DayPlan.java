package com.solutionscrafted.nutriplanner.entity.dayplan;

import com.solutionscrafted.nutriplanner.entity.Plan;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "day_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class DayPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer day;

    private String note;

    @Column(name = "total_calories")
    private Double totalCalories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "dayPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DayPlanRecipe> recipes = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "dayPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DayPlanActivity> activities = new HashSet<>();

    public void setRecipes(Set<DayPlanRecipe> recipes) {
        this.recipes.clear();
        if (recipes != null) {
            this.recipes.addAll(recipes);
        }
    }

    public void setActivities(Set<DayPlanActivity> activities) {
        this.activities.clear();
        if (activities != null) {
            this.activities.addAll(activities);
        }
    }
}

