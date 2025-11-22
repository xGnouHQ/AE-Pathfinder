package com.pathfinder.controller;

import com.pathfinder.dto.BewerbungDTORead;
import com.pathfinder.dto.BewerbungDTOWrite;
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
    public List<BewerbungDTORead> getAll() {
        return service.getAllDTO();
    }

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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/nachwuchskraft/{nwkId}")
    public List<BewerbungDTORead> getByNachwuchskraft(@PathVariable Long nwkId) {
        return service.getByNachwuchskraftDTO(nwkId);
    }

    @GetMapping("/stelle/{stelleId}")
    public List<BewerbungDTORead> getByStelle(@PathVariable Long stelleId) {
        return service.getByStelleDTO(stelleId);
    }
}
