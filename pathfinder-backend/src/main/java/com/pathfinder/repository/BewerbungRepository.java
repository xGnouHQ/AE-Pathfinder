package com.pathfinder.repository;

import com.pathfinder.model.Bewerbung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BewerbungRepository extends JpaRepository<Bewerbung, Long> {

    List<Bewerbung> findByNachwuchskraftId(Long nachwuchskraftId);
    List<Bewerbung> findByStelleId(Long stelleId);
}
