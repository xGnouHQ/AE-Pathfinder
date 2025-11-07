package com.pathfinder.service;

import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.repository.NachwuchskraftAnhangRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NachwuchskraftAnhangService {

    private final NachwuchskraftAnhangRepository repository;

    public NachwuchskraftAnhangService(NachwuchskraftAnhangRepository repository) {
        this.repository = repository;
    }

    public List<NachwuchskraftAnhang> getByNachwuchskraft(Long id) {
        return repository.findByNachwuchskraftId(id);
    }

    public NachwuchskraftAnhang save(NachwuchskraftAnhang anhang) {
        return repository.save(anhang);
    }

    public NachwuchskraftAnhang update(Long id, NachwuchskraftAnhang updated) {
        NachwuchskraftAnhang existing = repository.findById(id).orElseThrow();
        existing.setTyp(updated.getTyp());
        existing.setDateipfad(updated.getDateipfad());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
