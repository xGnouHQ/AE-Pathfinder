package com.pathfinder.service;

import com.pathfinder.model.Abteilung;
import com.pathfinder.repository.AbteilungRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbteilungService {

    private final AbteilungRepository repository;

    public AbteilungService(AbteilungRepository repository) {
        this.repository = repository;
    }

    public List<Abteilung> getAll() {
        return repository.findAll();
    }

    public Abteilung getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Abteilung save(Abteilung abteilung) {
        return repository.save(abteilung);
    }
}
