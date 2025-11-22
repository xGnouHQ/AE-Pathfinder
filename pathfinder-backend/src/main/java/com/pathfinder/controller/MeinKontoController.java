package com.pathfinder.controller;

import com.pathfinder.dto.meinKonto.ExperienceResponse;
import com.pathfinder.dto.meinKonto.ExperienceUpdateRequest;
import com.pathfinder.dto.meinKonto.PersonalDataResponse;
import com.pathfinder.exception.NachwuchskraftNotFoundException;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.service.NachwuchskraftService;
import com.pathfinder.service.NwkDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meinKonto")
@CrossOrigin
public class MeinKontoController {

    private final NachwuchskraftService nwkService;
    private final NwkDTOMapper mapper;

    public MeinKontoController(
            NachwuchskraftService nwkService,
            NwkDTOMapper mapper
    ) {
        this.nwkService = nwkService;
        this.mapper = mapper;
    }

    // ============ PERSONAL DATA =======================
    @GetMapping("/personal/{nwkId}")
    public ResponseEntity<PersonalDataResponse> getPersonalData(@PathVariable Long nwkId) {

        Nachwuchskraft nwk = nwkService.getOrThrow(nwkId);
        if (nwk == null) throw new NachwuchskraftNotFoundException(nwkId);

        return ResponseEntity.ok(mapper.toPersonalDataDTO(nwk));
    }

    // ============ EXPERIENCE DATA =======================
    @GetMapping("/experience/{nwkId}")
    public ResponseEntity<ExperienceResponse> getExperience(@PathVariable Long nwkId) {

        Nachwuchskraft nwk = nwkService.getOrThrow(nwkId);
        if (nwk == null) throw new NachwuchskraftNotFoundException(nwkId);

        return ResponseEntity.ok(mapper.toExperienceDTO(nwk));
    }

    // ============ UPDATE EXPERIENCE =======================
    @PutMapping("/experience/{nwkId}")
public ResponseEntity<ExperienceResponse> updateExperience(
            @PathVariable Long nwkId,
            @RequestBody ExperienceUpdateRequest req
    ) {
        Nachwuchskraft updated = nwkService.updateExperience(
                nwkId,
                req.interessenIds(),
                req.wunschabteilungenIds()
        );

        return ResponseEntity.ok(mapper.toExperienceDTO(updated));
    }
  
  @PostMapping("/nachwuchskraft/{nwkId}/experience")
public ResponseEntity<ExperienceResponse> updateExperience(
        @PathVariable Long nwkId,
        @RequestBody ExperienceUpdateRequest req
) {
    Nachwuchskraft existing = nwkService.getById(nwkId);
    if (existing == null) return ResponseEntity.notFound().build();

    // Wunschabteilungen aktualisieren
    if (req.getWunschabteilungenIds() != null) {
        List<Abteilung> neueAbteilungen = req.getWunschabteilungenIds().stream()
                .map(abteilungService::getById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        existing.setWunschabteilungen(neueAbteilungen);
    }

    // Interessen aktualisieren
    if (req.getInteressenIds() != null) {
        List<Tag> neueTags = req.getInteressenIds().stream()
                .map(tagService::getById) // Tags existieren ja schon
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        existing.setInteressen(neueTags);
    }

    Nachwuchskraft saved = nwkService.save(existing);

    // DTO für Response erzeugen
    ExperienceResponse response = new ExperienceResponse();
    response.setInteressen(saved.getInteressen().stream()
        .map(t -> new TagDTO(t.getId(), t.getName()))
        .toList());
    response.setWunschabteilungen(saved.getWunschabteilungen().stream()
        .map(a -> new AbteilungDTO(a.getId(), a.getName()))
        .toList());

    return ResponseEntity.ok(response);
}

}
