package com.pathfinder.repository;

import com.pathfinder.model.Nachwuchskraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NachwuchskraftRepository extends JpaRepository<Nachwuchskraft, Long> {
    Optional<Nachwuchskraft> findByEmail(String email);
}
