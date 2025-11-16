package com.pathfinder.repository;

import com.pathfinder.model.Abteilung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbteilungRepository extends JpaRepository<Abteilung, Long> {
}
