package com.solutionscrafted.nutriplanner.entity.dayplan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DayPlanRecipeId implements Serializable {

    @Column(name = "day_plan_id")
    private Long dayPlanId;

    @Column(name = "recipe_id")
    private Long recipeId;
}
