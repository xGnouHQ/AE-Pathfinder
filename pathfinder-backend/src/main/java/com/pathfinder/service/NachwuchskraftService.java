package com.pathfinder.service;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.repository.NachwuchskraftRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NachwuchskraftService {

    private final NachwuchskraftRepository repository;

    public NachwuchskraftService(NachwuchskraftRepository repository) {
        this.repository = repository;
    }

    public List<Nachwuchskraft> getAll() {
        return repository.findAll();
    }

    public Nachwuchskraft getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Nachwuchskraft save(Nachwuchskraft nwk) {
        return repository.save(nwk);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}