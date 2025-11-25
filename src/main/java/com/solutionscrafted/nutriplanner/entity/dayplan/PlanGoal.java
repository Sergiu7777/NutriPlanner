package com.solutionscrafted.nutriplanner.entity.dayplan;

public enum PlanGoal { //TODO: will use this in the future to calculate specific calorie deficit or surplus and the sport activity

    WEIGHT_LOSS("Slabire"),
    MASS_GAIN("Masa musculara"),
    KEEP_WEIGHT("Mentinere greutate");

    private final String goalName;

    PlanGoal(String goalName) {
        this.goalName = goalName;
    }
}
