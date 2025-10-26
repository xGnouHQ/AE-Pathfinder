package com.pathfinder.service;

import com.pathfinder.model.Bewerbung;
import com.pathfinder.repository.BewerbungRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BewerbungService {

    private final BewerbungRepository repository;

    public BewerbungService(BewerbungRepository repository) {
        this.repository = repository;
    }

    public List<Bewerbung> getAll() {
        return repository.findAll();
    }

    public Bewerbung getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Bewerbung save(Bewerbung bewerbung) {
        return repository.save(bewerbung);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Bewerbung> getByNachwuchskraft(Long nwkId) {
        return repository.findByNachwuchskraftId(nwkId);
    }

    public List<Bewerbung> getByStelle(Long stelleId) {
        return repository.findByStelleId(stelleId);
    }
}
