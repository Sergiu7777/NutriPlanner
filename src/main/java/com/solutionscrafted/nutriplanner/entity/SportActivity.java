package com.solutionscrafted.nutriplanner.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sport_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class SportActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    private Integer duration;
}
