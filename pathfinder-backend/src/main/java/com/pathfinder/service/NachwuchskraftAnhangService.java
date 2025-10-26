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

    public List<NachwuchskraftAnhang> getAll() {
        return repository.findAll();
    }

    public NachwuchskraftAnhang getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<NachwuchskraftAnhang> getByNachwuchskraft(Long nwkId) {
        return repository.findByNachwuchskraftId(nwkId);
    }

    public NachwuchskraftAnhang save(NachwuchskraftAnhang anhang) {
        return repository.save(anhang);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
