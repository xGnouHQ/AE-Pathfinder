package com.pathfinder.service;

import com.pathfinder.model.Servicebereichsleiter;
import com.pathfinder.repository.ServicebereichsleiterRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicebereichsleiterService {

    private final ServicebereichsleiterRepository repository;

    public ServicebereichsleiterService(ServicebereichsleiterRepository repository) {
        this.repository = repository;
    }

    public List<Servicebereichsleiter> getAll() {
        return repository.findAll();
    }

    public Servicebereichsleiter getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Servicebereichsleiter save(Servicebereichsleiter sbl) {
        return repository.save(sbl);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
