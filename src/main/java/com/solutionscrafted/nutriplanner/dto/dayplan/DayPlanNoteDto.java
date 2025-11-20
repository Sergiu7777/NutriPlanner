package com.solutionscrafted.nutriplanner.dto.dayplan;

import com.solutionscrafted.nutriplanner.dto.NoteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlanNoteDto {

    private Long dayPlanId;
    private Long noteId;

    private NoteDto note; // optional
}

