package com.pathfinder.controller;

import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.service.NachwuchskraftAnhangService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/anhaenge")
@CrossOrigin
public class NachwuchskraftAnhangController {

    private final NachwuchskraftAnhangService service;

    public NachwuchskraftAnhangController(NachwuchskraftAnhangService service) {
        this.service = service;
    }

    @GetMapping
    public List<NachwuchskraftAnhang> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public NachwuchskraftAnhang getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/nachwuchskraft/{nwkId}")
    public List<NachwuchskraftAnhang> getByNachwuchskraft(@PathVariable Long nwkId) {
        return service.getByNachwuchskraft(nwkId);
    }

    @PostMapping
    public NachwuchskraftAnhang create(@RequestBody NachwuchskraftAnhang anhang) {
        return service.save(anhang);
    }

    @PutMapping("/{id}")
    public NachwuchskraftAnhang update(@PathVariable Long id, @RequestBody NachwuchskraftAnhang anhang) {
        anhang.setId(id);
        return service.save(anhang);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
