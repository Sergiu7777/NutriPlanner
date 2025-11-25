package com.solutionscrafted.nutriplanner.entity.dayplan;

import lombok.Getter;

@Getter
public enum MealTimeEnum {
    BREAKFAST("Dejun", 30),
    LUNCH("Pranz", 45),
    DINNER("Cina", 20),
    SNACK("Gustare", 5);

    private final String meal;
    private final int intake;

    MealTimeEnum(String meal, int intake) {
        this.meal = meal;
        this.intake = intake;
    }
}
