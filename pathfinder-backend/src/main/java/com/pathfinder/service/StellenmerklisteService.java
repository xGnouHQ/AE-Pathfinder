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

    public List<Stellenmerkliste> getAll() {
        return repository.findAll();
    }

    public List<Stellenmerkliste> getByNachwuchskraft(Long id) {
        return repository.findByNachwuchskraftId(id);
    }

    public Stellenmerkliste save(Stellenmerkliste eintrag) {
        if (eintrag.getNachwuchskraft() == null || eintrag.getNachwuchskraft().getId() == null)
            throw new IllegalArgumentException("Nachwuchskraft-ID fehlt");
        if (eintrag.getStelle() == null || eintrag.getStelle().getId() == null)
            throw new IllegalArgumentException("Stellen-ID fehlt");

        Nachwuchskraft nwk = nwkRepository.findById(eintrag.getNachwuchskraft().getId())
                .orElseThrow(() -> new RuntimeException("Nachwuchskraft nicht gefunden"));
        Stelle stelle = stelleRepository.findById(eintrag.getStelle().getId())
                .orElseThrow(() -> new RuntimeException("Stelle nicht gefunden"));

        eintrag.setNachwuchskraft(nwk);
        eintrag.setStelle(stelle);
        eintrag.setId(new StellenmerklisteId(nwk.getId(), stelle.getId()));

        return repository.save(eintrag);
    }

    public void delete(Stellenmerkliste eintrag) {
        if (eintrag.getNachwuchskraft() == null || eintrag.getStelle() == null)
            throw new IllegalArgumentException("IDs fehlen beim Löschen");

        StellenmerklisteId id = new StellenmerklisteId(
                eintrag.getNachwuchskraft().getId(),
                eintrag.getStelle().getId()
        );
        repository.deleteById(id);
    }
}
