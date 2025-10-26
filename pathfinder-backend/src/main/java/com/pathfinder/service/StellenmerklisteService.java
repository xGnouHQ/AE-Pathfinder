package com.pathfinder.service;

import com.pathfinder.model.Stellenmerkliste;
import com.pathfinder.model.StellenmerklisteId;
import com.pathfinder.repository.StellenmerklisteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StellenmerklisteService {

    private final StellenmerklisteRepository repository;

    public StellenmerklisteService(StellenmerklisteRepository repository) {
        this.repository = repository;
    }

    public List<Stellenmerkliste> getAll() {
        return repository.findAll();
    }

    public List<Stellenmerkliste> getByNachwuchskraft(Long id) {
        return repository.findByNachwuchskraftId(id);
    }

    public Stellenmerkliste save(Stellenmerkliste eintrag) {
        return repository.save(eintrag);
    }

    public void delete(StellenmerklisteId id) {
        repository.deleteById(id);
    }
}
