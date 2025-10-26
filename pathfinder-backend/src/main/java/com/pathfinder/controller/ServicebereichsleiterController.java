package com.pathfinder.controller;

import com.pathfinder.model.Servicebereichsleiter;
import com.pathfinder.service.ServicebereichsleiterService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicebereichsleiter")
@CrossOrigin
public class ServicebereichsleiterController {

    private final ServicebereichsleiterService service;

    public ServicebereichsleiterController(ServicebereichsleiterService service) {
        this.service = service;
    }

    @GetMapping
    public List<Servicebereichsleiter> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Servicebereichsleiter getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Servicebereichsleiter create(@RequestBody Servicebereichsleiter sbl) {
        return service.save(sbl);
    }

    @PutMapping("/{id}")
    public Servicebereichsleiter update(@PathVariable Long id, @RequestBody Servicebereichsleiter sbl) {
        sbl.setId(id);
        return service.save(sbl);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
