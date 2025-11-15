package com.pathfinder.controller;

import com.pathfinder.dto.meinKonto.ExperienceResponse;
import com.pathfinder.dto.meinKonto.ExperienceUpdateRequest;
import com.pathfinder.dto.meinKonto.PersonalDataResponse;
import com.pathfinder.exception.NachwuchskraftNotFoundException;
import com.pathfinder.model.Abteilung;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Tag;
import com.pathfinder.service.AbteilungService;
import com.pathfinder.service.NachwuchskraftService;
import com.pathfinder.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meinKonto")
@CrossOrigin
public class MeinKontoController {

    private final NachwuchskraftService nwkService;
    private final AbteilungService abteilungService;
    private final TagService tagService;

    public MeinKontoController(
            NachwuchskraftService nwkService,
            AbteilungService abteilungService,
            TagService tagService) {
        this.nwkService = nwkService;
        this.abteilungService = abteilungService;
        this.tagService = tagService;
    }

    // ============ PERSONAL DATA =======================
    @GetMapping("/personal/{nwkId}")
    public ResponseEntity<PersonalDataResponse> getPersonalData(@PathVariable Long nwkId) {

        Nachwuchskraft nwk = nwkService.getOrThrow(nwkId);
        if (nwk == null) throw new NachwuchskraftNotFoundException(nwkId);

        PersonalDataResponse dto = new PersonalDataResponse(
                nwk.getId(),
                nwk.getVorname(),
                nwk.getNachname(),
                nwk.getEmail(),
                nwk.getStudienrichtung(),
                nwk.getJahrgang(),
                nwk.getPraktika()
        );

        return ResponseEntity.ok(dto);
    }

    // ============ EXPERIENCE DATA =======================
    @GetMapping("/experience/{nwkId}")
    public ResponseEntity<ExperienceResponse> getExperience(@PathVariable Long nwkId) {

        Nachwuchskraft nwk = nwkService.getOrThrow(nwkId);
        if (nwk == null) throw new NachwuchskraftNotFoundException(nwkId);

        ExperienceResponse dto = new ExperienceResponse(
                nwk.getId(),
                nwk.getInteressen(),
                nwk.getWunschabteilungen()
        );

        return ResponseEntity.ok(dto);
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

        return ResponseEntity.ok(
                new ExperienceResponse(
                        updated.getId(),
                        updated.getInteressen(),
                        updated.getWunschabteilungen()
                )
        );
    }
}
