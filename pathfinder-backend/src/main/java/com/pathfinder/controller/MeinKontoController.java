package com.pathfinder.controller;

import com.pathfinder.model.Abteilung;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Tag;
import com.pathfinder.service.AbteilungService;
import com.pathfinder.service.NachwuchskraftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.ArrayList;

import java.util.List;

@RestController
@RequestMapping("/api/meinKonto")
@CrossOrigin
public class MeinKontoController {

    private final NachwuchskraftService nwkService;
    private final AbteilungService abteilungService;

    public MeinKontoController(NachwuchskraftService nwkService, AbteilungService abteilungService) {
        this.nwkService = nwkService;
        this.abteilungService = abteilungService;
    }

    // ========================================================
    // GET /api/meinKonto/personal/{nwkId}
    // -> nur Anzeige persönlicher Daten + Praktika
    // ========================================================
    @GetMapping("/personal/{nwkId}")
    public ResponseEntity<?> getPersonalData(@PathVariable("nwkId") Long id) {

        Nachwuchskraft nwk = nwkService.getById(id);

        if (nwk == null) return ResponseEntity.notFound().build();

        var dto = new Object() {
            public final Long id = nwk.getId();
            public final String personalnummer = nwk.getPersonalnummer();
            public final String vorname = nwk.getVorname();
            public final String nachname = nwk.getNachname();
            public final String email = nwk.getEmail();
            public final String studienrichtung = nwk.getStudienrichtung();
            public final String jahrgang = nwk.getJahrgang();
            public final List<Abteilung> praktika = nwk.getPraktika();
        };

        return ResponseEntity.ok(dto);
    }

    // ========================================================
    // GET /api/meinKonto/experience/{nwkId}
    // -> Interessen + Wunschabteilungen anzeigen
    // ========================================================
    @GetMapping("/experience/{nwkId}")

    public ResponseEntity<?> getExperience(@PathVariable("nwkId") Long id) {
        Nachwuchskraft nwk = nwkService.getById(id);
        if (nwk == null) return ResponseEntity.notFound().build();

        var dto = new Object() {
            public final Long id = nwk.getId();
            public final List<Tag> interessen = nwk.getInteressen();
            public final List<Abteilung> wunschabteilungen = nwk.getWunschabteilungen();
        };

        return ResponseEntity.ok(dto);
    }


    // ========================================================
    // PUT /api/meinKonto/experience/{nwkId}
    // -> Interessen oder Wunschabteilungen ändern
    // ========================================================
    @PutMapping("/experience/{nwkId}")
    public ResponseEntity<?> updateExperience(
            @PathVariable("nwkId") Long id,
            @RequestBody Map<String, Object> updatedMap) {

        Nachwuchskraft existing = nwkService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        // Wunschabteilungen aktualisieren
        if (updatedMap.containsKey("wunschabteilungen")) {
            List<Map<String, Object>> abteilungen = (List<Map<String, Object>>) updatedMap.get("wunschabteilungen");
            List<Abteilung> neueAbteilungen = abteilungen.stream()
                    .map(a -> {
                        Long aid = ((Number) a.get("id")).longValue();
                        return abteilungService.getById(aid);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            existing.setWunschabteilungen(neueAbteilungen);
        }

        // Interessen aktualisieren
        if (updatedMap.containsKey("interessen")) {
            List<Map<String, Object>> tags = (List<Map<String, Object>>) updatedMap.get("interessen");
            List<com.pathfinder.model.Tag> neueTags = tags.stream()
                    .map(t -> {
                        com.pathfinder.model.Tag tag = new com.pathfinder.model.Tag();
                        tag.setId(((Number) t.get("id")).longValue());
                        tag.setName((String) t.get("name")); // optional
                        return tag;
                    })
                    .collect(Collectors.toList());
            existing.setInteressen(neueTags);
        }


        Nachwuchskraft saved = nwkService.save(existing);
        return ResponseEntity.ok(saved);
    }



}
