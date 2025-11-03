package com.pathfinder.repository;

import com.pathfinder.model.Stellenmerkliste;
import com.pathfinder.model.StellenmerklisteId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StellenmerklisteRepository extends JpaRepository<Stellenmerkliste, StellenmerklisteId> {
    List<Stellenmerkliste> findByNachwuchskraftId(Long nachwuchskraftId);
}
