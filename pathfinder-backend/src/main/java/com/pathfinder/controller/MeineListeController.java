package com.pathfinder.controller;

import com.pathfinder.model.Stellenmerkliste;
import com.pathfinder.service.StellenmerklisteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meineListe")
@CrossOrigin
public class MeineListeController {

    private final StellenmerklisteService service;

    public MeineListeController(StellenmerklisteService service) {
        this.service = service;
    }

    @PostMapping("/{stellenId}/merken/nachwuchskraft/{nachwuchskraftId}")
    public ResponseEntity<?> merkeStelle(
            @PathVariable Long stellenId,
            @PathVariable Long nachwuchskraftId) {
        try {
            Stellenmerkliste eintrag = service.addMerkliste(nachwuchskraftId, stellenId);
            return ResponseEntity.status(HttpStatus.CREATED).body(eintrag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Interner Serverfehler beim Merken der Stelle");
        }
    }



    // Nur NWK-spezifische Abfrage
    @GetMapping("/nachwuchskraft/{id}")
    public ResponseEntity<List<Stellenmerkliste>> getByNachwuchskraft(@PathVariable Long id) {
        try {
            List<Stellenmerkliste> eintraege = service.getByNachwuchskraft(id);
            if (eintraege.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204
            }
            return ResponseEntity.ok(eintraege);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    // NWK darf nur eigene Einträge löschen
    @DeleteMapping("/{stellenId}/nachwuchskraft/{nachwuchskraftId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long stellenId,
            @PathVariable Long nachwuchskraftId) {
        try {
            boolean deleted = service.deleteByNachwuchskraftAndStelle(nachwuchskraftId, stellenId);
            if (deleted) {
                return ResponseEntity.noContent().build(); // 204
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 – keine Berechtigung
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 – Eintrag nicht gefunden
        }
    }
}
