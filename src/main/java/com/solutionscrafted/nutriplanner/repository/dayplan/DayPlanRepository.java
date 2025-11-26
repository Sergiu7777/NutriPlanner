package com.solutionscrafted.nutriplanner.repository.dayplan;

import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DayPlanRepository extends JpaRepository<DayPlan, Long> {

    @Query("""
                select dp from DayPlan dp
                left join fetch dp.recipes dpr
                left join fetch dpr.recipe r
                left join fetch dp.activities act
                left join fetch act.activity sa
                where dp.plan.id = :planId
                order by dp.day
            """)
    Set<DayPlan> findByPlanId(@Param("planId") Long planId);
}

