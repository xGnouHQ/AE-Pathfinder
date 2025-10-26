package com.pathfinder.repository;

import com.pathfinder.model.Stelle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StelleRepository extends JpaRepository<Stelle, Long> {
}