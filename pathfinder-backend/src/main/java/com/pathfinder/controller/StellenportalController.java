package com.pathfinder.controller;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Stelle;
import com.pathfinder.model.Stellenmerkliste;
import com.pathfinder.model.StellenmerklisteId;
import com.pathfinder.repository.NachwuchskraftRepository;
import com.pathfinder.service.StelleService;
import com.pathfinder.service.StellenmerklisteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stellenportal")
@CrossOrigin
public class StellenportalController {

    private final StelleService stelleService;
    private final StellenmerklisteService merklisteService;
    private final NachwuchskraftRepository nwkRepository;

    public StellenportalController(StelleService stelleService,
                                   StellenmerklisteService merklisteService,
                                   NachwuchskraftRepository nwkRepository) {
        this.stelleService = stelleService;
        this.merklisteService = merklisteService;
        this.nwkRepository = nwkRepository;
    }

    // GET /api/stellenportal -> alle Stellen abrufen
    @GetMapping
    public ResponseEntity<List<Stelle>> getAllStellen() {
        List<Stelle> stellen = stelleService.getAll();
        return ResponseEntity.ok(stellen);
    }

    // GET /api/stellenportal/{id} -> Details einer bestimmten Stelle abrufen
    @GetMapping("/{id}")
    public ResponseEntity<?> getStelleById(@PathVariable Long id) {
        try {
            Stelle stelle = stelleService.getById(id);
            if (stelle == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(stelle);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Fehler beim Abrufen der Stelle: " + e.getMessage());
        }
    }

    // POST /api/stellenportal/{stellenid}/merken?nachwuchskraftId
    @PostMapping("/{stellenid}/merken")
    public ResponseEntity<?> merkeStelle(
            @PathVariable Long stellenid,
            @RequestParam Long nachwuchskraftId) {

        try {
            // NWK prüfen
            Nachwuchskraft nwk = nwkRepository.findById(nachwuchskraftId)
                    .orElseThrow(() -> new IllegalArgumentException("Nachwuchskraft nicht gefunden."));

            // Stelle prüfen
            Stelle stelle = stelleService.getById(stellenid);
            if (stelle == null) {
                return ResponseEntity.badRequest().body("Stelle nicht gefunden.");
            }

            // Validierung: NWK darf nur eigene Merkliste bearbeiten
            if (!nwk.getId().equals(nachwuchskraftId)) {
                return ResponseEntity.status(403).body("Keine Berechtigung für diese Merkliste.");
            }

            // neuen Eintrag erzeugen
            StellenmerklisteId id = new StellenmerklisteId(nachwuchskraftId, stellenid);
            Stellenmerkliste eintrag = new Stellenmerkliste();
            eintrag.setId(id);
            eintrag.setNachwuchskraft(nwk);
            eintrag.setStelle(stelle);

            merklisteService.save(eintrag);

            return ResponseEntity.ok("Stelle wurde erfolgreich zur Merkliste hinzugefügt.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Fehler beim Merken der Stelle: " + e.getMessage());
        }
    }
}
