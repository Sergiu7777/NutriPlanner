package com.solutionscrafted.nutriplanner.entity.dayplan;

import com.solutionscrafted.nutriplanner.entity.SportActivity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "day_plan_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlanActivity {

    @EmbeddedId
    private DayPlanActivityId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dayPlanId")
    @JoinColumn(name = "day_plan_id")
    private DayPlan dayPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("activityId")
    @JoinColumn(name = "activity_id")
    private SportActivity activity;
}

