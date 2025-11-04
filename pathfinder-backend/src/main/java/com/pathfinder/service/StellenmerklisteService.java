package com.pathfinder.service;

import com.pathfinder.model.*;
import com.pathfinder.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StellenmerklisteService {

    private final StellenmerklisteRepository repository;
    private final NachwuchskraftRepository nwkRepository;
    private final StelleRepository stelleRepository;

    public StellenmerklisteService(StellenmerklisteRepository repository,
                                   NachwuchskraftRepository nwkRepository,
                                   StelleRepository stelleRepository) {
        this.repository = repository;
        this.nwkRepository = nwkRepository;
        this.stelleRepository = stelleRepository;
    }

    public List<Stellenmerkliste> getByNachwuchskraft(Long id) {
        if (id == null) throw new IllegalArgumentException("Nachwuchskraft-ID fehlt");
        return repository.findByNachwuchskraftId(id);
    }

    // Validiertes Löschen
    public boolean deleteByNachwuchskraftAndStelle(Long nwkId, Long stelleId) {
        if (nwkId == null || stelleId == null)
            throw new IllegalArgumentException("IDs fehlen beim Löschen");

        StellenmerklisteId id = new StellenmerklisteId(nwkId, stelleId);
        Stellenmerkliste eintrag = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eintrag nicht gefunden"));

        // Zugriff nur wenn NWK-Eigentümer
        if (!eintrag.getNachwuchskraft().getId().equals(nwkId)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}
