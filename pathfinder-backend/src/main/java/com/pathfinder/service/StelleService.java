package com.pathfinder.service;

import com.pathfinder.model.Stelle;
import com.pathfinder.repository.StelleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StelleService {
    private final StelleRepository repository;

    public StelleService(StelleRepository repository) {
        this.repository = repository;
    }

    public List<Stelle> getAll() {
        return repository.findAll();
    }

    public Stelle getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Stelle save(Stelle stelle) {
        return repository.save(stelle);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
