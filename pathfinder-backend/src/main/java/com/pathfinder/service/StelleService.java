package com.pathfinder.service;

import com.pathfinder.model.Servicebereichsleiter;
import com.pathfinder.model.Stelle;
import com.pathfinder.model.Tag;
import com.pathfinder.repository.ServicebereichsleiterRepository;
import com.pathfinder.repository.StelleRepository;
import com.pathfinder.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StelleService {

    private final StelleRepository repository;
    private final ServicebereichsleiterRepository sblRepository;
    private final TagRepository tagRepository;

    public StelleService(StelleRepository repository,
                         ServicebereichsleiterRepository sblRepository,
                         TagRepository tagRepository) {
        this.repository = repository;
        this.sblRepository = sblRepository;
        this.tagRepository = tagRepository;
    }

    public List<Stelle> getAll() {
        return repository.findAll();
    }

    public Stelle getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Stelle save(Stelle stelle) {
        if (stelle.getServicebereichsleiter() != null && stelle.getServicebereichsleiter().getId() != null) {
            Servicebereichsleiter sbl = sblRepository.findById(stelle.getServicebereichsleiter().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Servicebereichsleiter mit ID " + stelle.getServicebereichsleiter().getId() + " nicht gefunden."));
            stelle.setServicebereichsleiter(sbl);
        } else {
            throw new IllegalArgumentException("Servicebereichsleiter darf nicht null sein.");
        }

        // Tags prüfen und ggf. neu anlegen
        if (stelle.getTags() != null) {
            List<Tag> managedTags = new ArrayList<>();
            for (Tag tag : stelle.getTags()) {
                // TagRepository wird hier NICHT mehr lokal überschrieben!
                Tag existing = tagRepository.findByName(tag.getName())
                        .orElseGet(() -> tagRepository.save(tag));
                managedTags.add(existing);
            }
            stelle.setTags(managedTags);
        }

        return repository.save(stelle);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
