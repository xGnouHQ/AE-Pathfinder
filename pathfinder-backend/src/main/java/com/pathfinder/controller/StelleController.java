package com.pathfinder.controller;

import com.pathfinder.model.Stelle;
import com.pathfinder.service.StelleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stellen")
@CrossOrigin
public class StelleController {

    private final StelleService service;

    public StelleController(StelleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Stelle> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Stelle getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Stelle create(@RequestBody Stelle stelle) {
        return service.save(stelle);
    }

    @PutMapping("/{id}")
    public Stelle update(@PathVariable Long id, @RequestBody Stelle stelle) {
        stelle.setId(id);
        return service.save(stelle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
