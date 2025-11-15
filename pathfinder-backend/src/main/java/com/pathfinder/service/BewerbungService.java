package com.pathfinder.service;

import com.pathfinder.exception.DuplicateBewerbungException;
import com.pathfinder.model.Bewerbung;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Stelle;
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

    public BewerbungService(BewerbungRepository repository,
                            NachwuchskraftRepository nwkRepository,
                            StelleRepository stelleRepository) {
        this.repository = repository;
        this.nwkRepository = nwkRepository;
        this.stelleRepository = stelleRepository;
    }

    public List<Bewerbung> getAll() {
        return repository.findAll();
    }

    public Bewerbung getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bewerbung nicht gefunden"));
    }

    public Bewerbung save(Bewerbung b) {
        if (b.getNachwuchskraft() == null || b.getNachwuchskraft().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nachwuchskraft-ID fehlt oder ist null");
        }
        if (b.getStelle() == null || b.getStelle().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stelle-ID fehlt oder ist null");
        }

        // Objekte aus DB laden
        Nachwuchskraft nwk = nwkRepository.findById(b.getNachwuchskraft().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Nachwuchskraft mit ID " + b.getNachwuchskraft().getId() + " nicht gefunden"));
        Stelle stelle = stelleRepository.findById(b.getStelle().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Stelle mit ID " + b.getStelle().getId() + " nicht gefunden"));

        b.setNachwuchskraft(nwk);
        b.setStelle(stelle);

        if (repository.existsByNachwuchskraftIdAndStelleId(
                b.getNachwuchskraft().getId(),
                b.getStelle().getId())) {

            throw new DuplicateBewerbungException();
        }


        return repository.save(b);
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
