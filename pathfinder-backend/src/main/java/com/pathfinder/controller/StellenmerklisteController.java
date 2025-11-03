package com.pathfinder.controller;

import com.pathfinder.model.Stellenmerkliste;
import com.pathfinder.service.StellenmerklisteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stellenmerkliste")
@CrossOrigin
public class StellenmerklisteController {

    private final StellenmerklisteService service;

    public StellenmerklisteController(StellenmerklisteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Stellenmerkliste> getAll() {
        return service.getAll();
    }

    @GetMapping("/nachwuchskraft/{id}")
    public List<Stellenmerkliste> getByNachwuchskraft(@PathVariable Long id) {
        return service.getByNachwuchskraft(id);
    }

    @PostMapping
    public Stellenmerkliste create(@RequestBody Stellenmerkliste eintrag) {
        return service.save(eintrag);
    }

    @DeleteMapping
    public void delete(@RequestBody Stellenmerkliste eintrag) {
        service.delete(eintrag);
    }
}
