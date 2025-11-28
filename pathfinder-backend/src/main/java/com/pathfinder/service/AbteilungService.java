package com.pathfinder.service;

import com.pathfinder.exception.AbteilungNotFoundException;
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

    public Abteilung getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AbteilungNotFoundException(id));
    }

    public List<Abteilung> getByIds(List<Long> ids) {
        return ids.stream()
                .map(this::getById)
                .toList();
    }

    public List<Abteilung> getAll() {
        return repository.findAll();
    }
}
