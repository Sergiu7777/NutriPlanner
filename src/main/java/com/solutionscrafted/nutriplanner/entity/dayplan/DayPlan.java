package com.solutionscrafted.nutriplanner.entity.dayplan;

import com.solutionscrafted.nutriplanner.entity.Plan;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "day_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer day;

    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "dayPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayPlanRecipe> recipes = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "dayPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayPlanActivity> activities = new ArrayList<>();

    public void setRecipes(List<DayPlanRecipe> recipes) {
        this.recipes.clear();
        if (recipes != null) {
            this.recipes.addAll(recipes);
        }
    }

    public void setActivities(List<DayPlanActivity> activities) {
        this.activities.clear();
        if (activities != null) {
            this.activities.addAll(activities);
        }
    }
}

