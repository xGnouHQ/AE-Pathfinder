package com.pathfinder.service;

import com.pathfinder.model.Bewerbung;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Stelle;
import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.repository.BewerbungRepository;
import com.pathfinder.repository.NachwuchskraftRepository;
import com.pathfinder.repository.StelleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BewerbungService {

    private final BewerbungRepository repository;
    private final NachwuchskraftRepository nwkRepository;
    private final StelleRepository stelleRepository;
    private final NachwuchskraftAnhangService anhangService;

    public BewerbungService(BewerbungRepository repository,
                            NachwuchskraftRepository nwkRepository,
                            StelleRepository stelleRepository,
                            NachwuchskraftAnhangService anhangService) {
        this.repository = repository;
        this.nwkRepository = nwkRepository;
        this.stelleRepository = stelleRepository;
        this.anhangService = anhangService;
    }

    public List<Bewerbung> getAll() {
        return repository.findAll();
    }

    public Bewerbung getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bewerbung nicht gefunden"));
    }

    public Bewerbung createBewerbung(Long stelleId, Long nwkId, List<Long> fileIds, String hrNote) {
        // Nachwuchskraft laden
        Nachwuchskraft nwk = nwkRepository.findById(nwkId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Nachwuchskraft mit ID " + nwkId + " nicht gefunden"));

        // Stelle laden
        Stelle stelle = stelleRepository.findById(stelleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Stelle mit ID " + stelleId + " nicht gefunden"));

        // Bewerbung erstellen
        Bewerbung b = new Bewerbung();
        b.setNachwuchskraft(nwk);
        b.setStelle(stelle);
        b.setKommentar(hrNote);

        // Zuerst Bewerbung speichern, damit die ID gesetzt wird
        b = repository.save(b);

        // Optional: Anhänge zuordnen
        if (fileIds != null) {
            for (Long fileId : fileIds) {
                NachwuchskraftAnhang anhang = anhangService.getById(fileId);
                if (anhang != null) {
                    anhang.setBewerbung(b);
                    anhangService.save(anhang);
                }
            }
        }

        return b;
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bewerbung nicht gefunden");
        }
        repository.deleteById(id);
    }

    public List<Bewerbung> getByNachwuchskraft(Long nwkId) {
        return repository.findByNachwuchskraftId(nwkId);
    }

    public List<Bewerbung> getByStelle(Long stelleId) {
        return repository.findByStelleId(stelleId);
    }
}
