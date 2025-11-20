package com.solutionscrafted.nutriplanner.entity.dayplan;

import com.solutionscrafted.nutriplanner.entity.Note;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "day_plan_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlanNote {

    @EmbeddedId
    private DayPlanNoteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dayPlanId")
    @JoinColumn(name = "day_plan_id")
    private DayPlan dayPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("noteId")
    @JoinColumn(name = "note_id")
    private Note note;
}

