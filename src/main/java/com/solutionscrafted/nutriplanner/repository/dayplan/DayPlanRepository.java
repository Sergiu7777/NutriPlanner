package com.solutionscrafted.nutriplanner.repository.dayplan;

import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayPlanRepository extends JpaRepository<DayPlan, Long> {
    List<DayPlan> findByPlanId(Long planId);
}

