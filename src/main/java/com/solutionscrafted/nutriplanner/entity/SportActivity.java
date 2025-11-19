package com.solutionscrafted.nutriplanner.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plan_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportActivity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String activity;

  @OneToOne
  @JoinColumn(name = "plan_id")
  private Plan plan;
}
