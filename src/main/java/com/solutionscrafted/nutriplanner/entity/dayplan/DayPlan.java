package com.solutionscrafted.nutriplanner.entity.dayplan;

import com.solutionscrafted.nutriplanner.entity.Plan;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "day_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer day;

    private String note;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "dayPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayPlanRecipe> recipes = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "dayPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayPlanActivity> activities = new ArrayList<>();
}

