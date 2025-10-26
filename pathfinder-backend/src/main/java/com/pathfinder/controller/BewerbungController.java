package com.pathfinder.controller;

import com.pathfinder.model.Bewerbung;
import com.pathfinder.service.BewerbungService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bewerbungen")
@CrossOrigin
public class BewerbungController {

    private final BewerbungService service;

    public BewerbungController(BewerbungService service) {
        this.service = service;
    }

    @GetMapping
    public List<Bewerbung> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Bewerbung getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Bewerbung create(@RequestBody Bewerbung bewerbung) {
        return service.save(bewerbung);
    }

    @PutMapping("/{id}")
    public Bewerbung update(@PathVariable Long id, @RequestBody Bewerbung bewerbung) {
        bewerbung.setId(id);
        return service.save(bewerbung);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/nachwuchskraft/{nwkId}")
    public List<Bewerbung> getByNachwuchskraft(@PathVariable Long nwkId) {
        return service.getByNachwuchskraft(nwkId);
    }

    @GetMapping("/stelle/{stelleId}")
    public List<Bewerbung> getByStelle(@PathVariable Long stelleId) {
        return service.getByStelle(stelleId);
    }
}
