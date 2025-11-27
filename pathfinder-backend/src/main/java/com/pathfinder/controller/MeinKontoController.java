package com.pathfinder.controller;

import com.pathfinder.dto.meinKonto.*;
import com.pathfinder.exception.NachwuchskraftNotFoundException;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.service.*;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meinKonto")
@CrossOrigin
public class MeinKontoController {

    private final NachwuchskraftService nwkService;
    private final NwkDTOMapper mapper;
    private final TagService tagService;
    private final AbteilungService abteilungService;

    public MeinKontoController(
            NachwuchskraftService nwkService,
            NwkDTOMapper mapper,
            TagService tagService,
            AbteilungService abteilungService
    ) {
        this.nwkService = nwkService;
        this.mapper = mapper;
        this.tagService = tagService;
        this.abteilungService = abteilungService;
    }

    // personal Daten
    @GetMapping("/personal/{nwkId}")
    public ResponseEntity<PersonalDataResponse> getPersonalData(@PathVariable Long nwkId) {

        Nachwuchskraft nwk = nwkService.getOrThrow(nwkId);
        if (nwk == null) throw new NachwuchskraftNotFoundException(nwkId);

        return ResponseEntity.ok(mapper.toPersonalDataDTO(nwk));
    }

    // experience Daten
    @GetMapping("/experience/{nwkId}")
    public ResponseEntity<ExperienceResponse> getExperience(@PathVariable Long nwkId) {

        Nachwuchskraft nwk = nwkService.getOrThrow(nwkId);
        if (nwk == null) throw new NachwuchskraftNotFoundException(nwkId);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache().mustRevalidate())
                .body(mapper.toExperienceDTO(nwk));
    }


    // UPDATE experience
    @PutMapping("/experience/{nwkId}")
    public ResponseEntity<ExperienceResponse> updateExperience(
            @PathVariable Long nwkId,
            @RequestBody ExperienceUpdateRequest req
    ) {
        Nachwuchskraft updated = nwkService.updateExperience(
                nwkId,
                req.interessenIds(),
                req.wunschabteilungenIds(),
                req.programmieren(),
                req.programmiersprachen()
        );

        return ResponseEntity.ok(mapper.toExperienceDTO(updated));
    }

    // Optionen für die Dropdown
    @GetMapping("/options")
    public ResponseEntity<OptionsResponse> getOptions() {

        var tags = tagService.getAll();
        var abteilungen = abteilungService.getAll();

        return ResponseEntity.ok(
                new OptionsResponse(
                        mapper.toTagDTOList(tags),
                        mapper.toAbteilungDTOList(abteilungen)
                )
        );
    }
}
