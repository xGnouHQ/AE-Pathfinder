package com.pathfinder.controller;

import com.pathfinder.dto.stelle.StelleDetailDTO;
import com.pathfinder.dto.stelle.StelleListDTO;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Stelle;
import com.pathfinder.model.Stellenmerkliste;
import com.pathfinder.model.StellenmerklisteId;
import com.pathfinder.repository.NachwuchskraftRepository;
import com.pathfinder.dto.stelle.StelleDTOMapper;
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
    private final StelleDTOMapper mapper;

    public StellenportalController(StelleService stelleService,
                                   StellenmerklisteService merklisteService,
                                   NachwuchskraftRepository nwkRepository,
                                   StelleDTOMapper mapper) {
        this.stelleService = stelleService;
        this.merklisteService = merklisteService;
        this.nwkRepository = nwkRepository;
        this.mapper = mapper;
    }

    // GET: Alle Stellen als DTO
    @GetMapping
    public ResponseEntity<List<StelleListDTO>> getAllStellen() {
        List<StelleListDTO> dtos = stelleService.getAll().stream()
                .map(mapper::toListDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // GET: Stelle im Detail
    @GetMapping("/{id}")
    public ResponseEntity<?> getStelleById(@PathVariable Long id) {
        Stelle stelle = stelleService.getById(id);
        if (stelle == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(mapper.toDetailDTO(stelle));
    }

    // POST: Stelle merken
    @PostMapping("/{stellenid}/merken")
    public ResponseEntity<?> merkeStelle(
            @PathVariable Long stellenid,
            @RequestParam Long nachwuchskraftId) {

        Nachwuchskraft nwk = nwkRepository.findById(nachwuchskraftId)
                .orElseThrow(() -> new IllegalArgumentException("Nachwuchskraft nicht gefunden."));

        Stelle stelle = stelleService.getById(stellenid);
        if (stelle == null) return ResponseEntity.badRequest().body("Stelle nicht gefunden.");

        StellenmerklisteId id = new StellenmerklisteId(nachwuchskraftId, stellenid);
        Stellenmerkliste eintrag = new Stellenmerkliste();
        eintrag.setId(id);
        eintrag.setNachwuchskraft(nwk);
        eintrag.setStelle(stelle);

        merklisteService.save(eintrag);

        return ResponseEntity.ok("Stelle wurde erfolgreich zur Merkliste hinzugefügt.");
    }
}
