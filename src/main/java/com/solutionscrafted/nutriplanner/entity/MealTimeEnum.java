package com.solutionscrafted.nutriplanner.entity;

import java.time.LocalTime;

public enum MealTimeEnum {
    BREAKFAST(LocalTime.of(8, 0)),
    LUNCH(LocalTime.of(12, 0)),
    DINNER(LocalTime.of(18, 0)),
    SNACK(LocalTime.of(15, 0));

    MealTimeEnum(LocalTime mealTime) {

    }
}
