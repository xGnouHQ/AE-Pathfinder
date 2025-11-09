package com.pathfinder.controller;

import com.pathfinder.model.Abteilung;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.service.AbteilungService;
import com.pathfinder.service.NachwuchskraftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            public final String vorname = nwk.getVorname();
            public final String nachname = nwk.getNachname();
            public final String email = nwk.getEmail();
            public final String studienrichtung = nwk.getStudienrichtung();
            public final String jahrgang = nwk.getJahrgang();
            public final String praktika = nwk.getPraktika();
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
            public final String interessen = nwk.getInteressen();
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
            @RequestBody Nachwuchskraft updated) {

        Nachwuchskraft existing = nwkService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        // Interessen direkt übernehmen
        existing.setInteressen(updated.getInteressen());

        // Wunschabteilungen aktualisieren
        if (updated.getWunschabteilungen() != null) {
            List<Abteilung> neueAbteilungen = updated.getWunschabteilungen().stream()
                    .map(a -> abteilungService.getById(a.getId()))
                    .filter(a -> a != null)
                    .collect(Collectors.toCollection(ArrayList::new));

            existing.setWunschabteilungen(neueAbteilungen);
        }

        Nachwuchskraft saved = nwkService.save(existing);
        return ResponseEntity.ok(saved);
    }
}
