package com.solutionscrafted.nutriplanner.repository;

import com.solutionscrafted.nutriplanner.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
