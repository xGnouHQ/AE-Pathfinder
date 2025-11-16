package com.pathfinder.controller;

import com.pathfinder.model.Bewerbung;
import com.pathfinder.service.BewerbungService;
import com.pathfinder.service.NachwuchskraftAnhangService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bewerbungen")
@CrossOrigin
public class BewerbungController {

    private final BewerbungService bewerbungService;
    private final NachwuchskraftAnhangService anhangService;

    public BewerbungController(BewerbungService bewerbungService, NachwuchskraftAnhangService anhangService) {
        this.bewerbungService = bewerbungService;
        this.anhangService = anhangService;
    }

    // --- Alle Bewerbungen
    @GetMapping
    public List<Bewerbung> getAll() {
        return bewerbungService.getAll();
    }

    // --- Einzelne Bewerbung
    @GetMapping("/{id}")
    public Bewerbung getOne(@PathVariable Long id) {
        return bewerbungService.getById(id);
    }

    @PostMapping(consumes = "application/json")
    public Bewerbung createBewerbung(@RequestBody java.util.Map<String, Object> payload) {
        try {
            // Pflichtfelder prüfen
            Object stelleObj = payload.get("stelleId");
            Object nwkObj = payload.get("nachwuchskraftId");

            if (stelleObj == null || nwkObj == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "stelleId oder nachwuchskraftId fehlt");
            }

            Long stelleId = Long.valueOf(stelleObj.toString());
            Long nwkId = Long.valueOf(nwkObj.toString());

            // Optional: Datei-IDs auslesen, falls vorhanden
            @SuppressWarnings("unchecked")
            java.util.List<Long> fileIds = null;
            Object filesObj = payload.get("fileIds");
            if (filesObj instanceof java.util.List<?>) {
                fileIds = ((java.util.List<Object>) filesObj).stream()
                        .map(o -> Long.valueOf(o.toString()))
                        .toList();
            }

            // Optional: HR-Notiz
            String hrNote = payload.get("hrNote") != null ? payload.get("hrNote").toString() : null;

            return bewerbungService.createBewerbung(stelleId, nwkId, fileIds, hrNote);
        } catch (ResponseStatusException e) {
            throw e; // BAD_REQUEST weiterleiten
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Fehler beim Erstellen der Bewerbung: " + e.getMessage());
        }
    }


    // --- Bewerbung löschen
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bewerbungService.delete(id);
    }

    // --- Nach Nachwuchskraft filtern
    @GetMapping("/nachwuchskraft/{nwkId}")
    public List<Bewerbung> getByNachwuchskraft(@PathVariable Long nwkId) {
        return bewerbungService.getByNachwuchskraft(nwkId);
    }

    // --- Nach Stelle filtern
    @GetMapping("/stelle/{stelleId}")
    public List<Bewerbung> getByStelle(@PathVariable Long stelleId) {
        return bewerbungService.getByStelle(stelleId);
    }
}
