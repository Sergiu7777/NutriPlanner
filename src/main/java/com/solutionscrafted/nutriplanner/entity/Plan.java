package com.solutionscrafted.nutriplanner.entity;

import com.solutionscrafted.nutriplanner.entity.dayplan.DayPlan;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Double totalCalories;

    private Integer numberOfDays;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    //    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayPlan> dayPlans;
}

