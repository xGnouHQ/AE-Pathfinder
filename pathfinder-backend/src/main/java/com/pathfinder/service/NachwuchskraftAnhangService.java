package com.pathfinder.service;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.repository.NachwuchskraftAnhangRepository;
import com.pathfinder.repository.NachwuchskraftRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class NachwuchskraftAnhangService {

    private final NachwuchskraftAnhangRepository repository;
    private final NachwuchskraftRepository nwkRepository;

    public NachwuchskraftAnhangService(NachwuchskraftAnhangRepository repository,
                                       NachwuchskraftRepository nwkRepository) {
        this.repository = repository;
        this.nwkRepository = nwkRepository;
    }

    // Alle Anhänge abrufen
    public List<NachwuchskraftAnhang> getAll() {
        return repository.findAll();
    }

    // Anhang nach ID abrufen
    public NachwuchskraftAnhang getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anhang nicht gefunden"));
    }

    // Alle Anhänge einer bestimmten Nachwuchskraft abrufen
    public List<NachwuchskraftAnhang> getByNachwuchskraft(Long id) {
        return repository.findByNachwuchskraftId(id);
    }

    // Anhang speichern (inkl. Laden der echten Nachwuchskraft aus DB)
    public NachwuchskraftAnhang save(NachwuchskraftAnhang anhang) {
        if (anhang.getNachwuchskraft() == null || anhang.getNachwuchskraft().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nachwuchskraft-ID fehlt oder ist null");
        }

        Nachwuchskraft nwk = nwkRepository.findById(anhang.getNachwuchskraft().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nachwuchskraft mit ID " + anhang.getNachwuchskraft().getId() + " nicht gefunden"));

        anhang.setNachwuchskraft(nwk);
        return repository.save(anhang);
    }

    // Anhang löschen
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anhang nicht gefunden");
        }
        repository.deleteById(id);
    }
}
