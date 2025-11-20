package com.solutionscrafted.nutriplanner.repository.dayplan;

import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanActivity;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanActivityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayPlanActivityRepository extends JpaRepository<DayPlanActivity, DayPlanActivityId> {
}

