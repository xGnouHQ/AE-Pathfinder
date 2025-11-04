package com.pathfinder.controller;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.service.NachwuchskraftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meinKonto")
@CrossOrigin
public class MeinKontoController {

    private final NachwuchskraftService service;

    public MeinKontoController(NachwuchskraftService service) {
        this.service = service;
    }

    // GET /api/meinKonto/personal/{id}
    @GetMapping("/personal/{id}")
    public ResponseEntity<?> getPersonalData(@PathVariable Long id) {
        Nachwuchskraft nwk = service.getById(id);
        if (nwk == null) return ResponseEntity.notFound().build();

        // Nur relevante persönliche Daten zurückgeben
        var dto = new Object() {
            public final Long id = nwk.getId();
            public final String vorname = nwk.getVorname();
            public final String nachname = nwk.getNachname();
            public final String email = nwk.getEmail();
            public final String studienrichtung = nwk.getStudienrichtung();
            public final int eintrittsjahr = nwk.getEintrittsjahr();
        };

        return ResponseEntity.ok(dto);
    }

    // GET /api/meinKonto/experience/{id}
    @GetMapping("/experience/{id}")
    public ResponseEntity<Nachwuchskraft> getExperience(@PathVariable Long id) {
        Nachwuchskraft nwk = service.getById(id);
        if (nwk == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(nwk);
    }

    // PUT /api/meinKonto/experience/{id}
    @PutMapping("/experience/{id}")
    public ResponseEntity<Nachwuchskraft> updateExperience(
            @PathVariable Long id,
            @RequestBody Nachwuchskraft updated) {

        Nachwuchskraft existing = service.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setInteressen(updated.getInteressen());
        existing.setErfahrungen(updated.getErfahrungen());

        Nachwuchskraft saved = service.save(existing);
        return ResponseEntity.ok(saved);
    }
}
