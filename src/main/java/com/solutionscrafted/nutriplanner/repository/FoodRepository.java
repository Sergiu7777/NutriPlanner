package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}

