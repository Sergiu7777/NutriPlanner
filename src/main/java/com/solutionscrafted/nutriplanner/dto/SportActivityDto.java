package com.solutionscrafted.nutriplanner.dto;

import lombok.Builder;

@Builder
public record SportActivityDto(
        Long id,
        String name,
        Integer duration
) {
}

