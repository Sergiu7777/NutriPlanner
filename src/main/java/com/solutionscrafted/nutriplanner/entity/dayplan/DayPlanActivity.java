package com.solutionscrafted.nutriplanner.entity.dayplan;

import com.solutionscrafted.nutriplanner.entity.SportActivity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "day_plan_activity")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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

