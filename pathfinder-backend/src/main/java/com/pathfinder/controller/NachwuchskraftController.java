package com.pathfinder.controller;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.service.NachwuchskraftService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/nachwuchskraft")
@CrossOrigin
public class NachwuchskraftController {

    private final NachwuchskraftService service;

    public NachwuchskraftController(NachwuchskraftService service) {
        this.service = service;
    }

    @GetMapping
    public List<Nachwuchskraft> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Nachwuchskraft getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Nachwuchskraft create(@RequestBody Nachwuchskraft nwk) {
        return service.save(nwk);
    }

    @PutMapping("/{id}")
    public Nachwuchskraft update(@PathVariable Long id, @RequestBody Nachwuchskraft nwk) {
        nwk.setId(id);
        return service.save(nwk);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
