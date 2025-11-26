package com.solutionscrafted.nutriplanner.entity.dayplan;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DayPlanActivityId implements Serializable {

    private Long dayPlanId;
    private Long activityId;
}

