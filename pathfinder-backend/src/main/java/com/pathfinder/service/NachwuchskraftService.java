package com.pathfinder.service;

import com.pathfinder.exception.NachwuchskraftNotFoundException;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.repository.NachwuchskraftRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NachwuchskraftService {

    private final NachwuchskraftRepository repository;
    private final TagService tagService;
    private final AbteilungService abteilungService;

    public NachwuchskraftService(
            NachwuchskraftRepository repository,
            TagService tagService,
            AbteilungService abteilungService
    ) {
        this.repository = repository;
        this.tagService = tagService;
        this.abteilungService = abteilungService;
    }

    public Nachwuchskraft getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NachwuchskraftNotFoundException(id));
    }

    public Nachwuchskraft save(Nachwuchskraft nwk) {
        return repository.save(nwk);
    }

    public Nachwuchskraft updateExperience(
            Long nwkId,
            List<Long> interessenIds,
            List<Long> wunschabteilungenIds
    ) {
        Nachwuchskraft nwk = getOrThrow(nwkId);

        // Interessen
        if (interessenIds != null) {
            nwk.getInteressen().clear();
            if (!interessenIds.isEmpty()) {
                nwk.getInteressen().addAll(tagService.getByIds(interessenIds));
            }
        }

        // Wunschabteilungen
        if (wunschabteilungenIds != null) {
            nwk.getWunschabteilungen().clear();
            if (!wunschabteilungenIds.isEmpty()) {
                nwk.getWunschabteilungen().addAll(abteilungService.getByIds(wunschabteilungenIds));
            }
        }

        return save(nwk);
    }
}
