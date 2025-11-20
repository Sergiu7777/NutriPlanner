package com.solutionscrafted.nutriplanner.entity.dayplan;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayPlanActivityId implements Serializable {

    private Long dayPlanId;
    private Long activityId;
}

