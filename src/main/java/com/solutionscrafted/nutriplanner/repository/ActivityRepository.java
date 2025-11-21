package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.SportActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<SportActivity, Long> {
}
