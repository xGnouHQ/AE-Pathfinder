package com.pathfinder.controller;

import com.pathfinder.dto.meineListe.MeineListeItemDTO;
import com.pathfinder.model.Stellenmerkliste;
import com.pathfinder.service.MeineListeMapper;
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
    private final MeineListeMapper mapper;

    public MeineListeController(StellenmerklisteService service,
                                MeineListeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ==================================================
    // POST: Stelle merken
    // ==================================================
    @PostMapping("/{stellenId}/merken/nachwuchskraft/{nachwuchskraftId}")
    public ResponseEntity<?> merkeStelle(
            @PathVariable Long stellenId,
            @PathVariable Long nachwuchskraftId) {
        try {
            Stellenmerkliste eintrag = service.addMerkliste(nachwuchskraftId, stellenId);
            MeineListeItemDTO dto = mapper.toDTO(eintrag);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Interner Serverfehler beim Merken der Stelle");
        }
    }


    // ==================================================
    // GET: Alle gemerkten Stellen eines NWK
    // ==================================================
    @GetMapping("/nachwuchskraft/{id}")
    public ResponseEntity<List<MeineListeItemDTO>> getByNachwuchskraft(@PathVariable Long id) {
        try {
            List<Stellenmerkliste> eintraege = service.getByNachwuchskraft(id);

            if (eintraege.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204
            }

            List<MeineListeItemDTO> dtos = eintraege.stream()
                    .map(mapper::toDTO)
                    .toList();

            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }


    // ==================================================
    // DELETE: Eintrag für NWK löschen
    // ==================================================
    @DeleteMapping("/{stellenId}/nachwuchskraft/{nachwuchskraftId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long stellenId,
            @PathVariable Long nachwuchskraftId) {

        try {
            boolean deleted = service.deleteByNachwuchskraftAndStelle(nachwuchskraftId, stellenId);

            if (deleted) {
                return ResponseEntity.noContent().build(); // 204
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
