package com.solutionscrafted.nutriplanner.repository.dayplan;

import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanNote;
import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlanNoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayPlanNoteRepository extends JpaRepository<DayPlanNote, DayPlanNoteId> {
}

