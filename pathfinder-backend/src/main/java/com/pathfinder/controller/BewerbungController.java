package com.pathfinder.controller;

import com.pathfinder.dto.BewerbungDTORead;
import com.pathfinder.dto.BewerbungDTOWrite;
import com.pathfinder.service.BewerbungService;
import com.pathfinder.service.NachwuchskraftAnhangService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<BewerbungDTORead> getAll() {
        return service.getAllDTO();
    }

    // --- Einzelne Bewerbung
    @GetMapping("/{id}")
    public BewerbungDTORead getOne(@PathVariable Long id) {
        return service.getDTOById(id);
    }

    @PostMapping
    public BewerbungDTORead create(@RequestBody BewerbungDTOWrite dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public BewerbungDTORead update(@PathVariable Long id, @RequestBody BewerbungDTOWrite dto) {
        return service.update(id, dto);
    }


    // --- Bewerbung löschen
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bewerbungService.delete(id);
    }

    // --- Nach Nachwuchskraft filtern
    @GetMapping("/nachwuchskraft/{nwkId}")
    public List<BewerbungDTORead> getByNachwuchskraft(@PathVariable Long nwkId) {
        return service.getByNachwuchskraftDTO(nwkId);
    }

    // --- Nach Stelle filtern
    @GetMapping("/stelle/{stelleId}")
    public List<BewerbungDTORead> getByStelle(@PathVariable Long stelleId) {
        return service.getByStelleDTO(stelleId);
    }
}
