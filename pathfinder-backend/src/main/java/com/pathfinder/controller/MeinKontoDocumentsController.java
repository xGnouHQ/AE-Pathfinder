package com.pathfinder.controller;

import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.service.NachwuchskraftAnhangService;
import com.pathfinder.service.NachwuchskraftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/meinKonto/documents")
@CrossOrigin
public class MeinKontoDocumentsController {

    private final NachwuchskraftAnhangService service;
    private final NachwuchskraftService nwkService;

    public MeinKontoDocumentsController(NachwuchskraftAnhangService service, NachwuchskraftService nwkService) {
        this.service = service;
        this.nwkService = nwkService;
    }

    @GetMapping("/{nwkId}")
    public ResponseEntity<List<NachwuchskraftAnhang>> getDocuments(@PathVariable Long nwkId) {
        List<NachwuchskraftAnhang> docs = service.getByNachwuchskraft(nwkId);
        if (docs.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(docs);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<NachwuchskraftAnhang> uploadDocument(
            @RequestParam("nwkId") Long nwkId,
            @RequestParam("file") MultipartFile file) {

        try {
            NachwuchskraftAnhang saved = service.saveFile(nwkId, file);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<NachwuchskraftAnhang> updateDocument(
            @PathVariable Long documentId,
            @RequestBody NachwuchskraftAnhang updated) {

        NachwuchskraftAnhang existing = service.getById(documentId);
        existing.setDateipfad(updated.getDateipfad());
        existing.setTyp(updated.getTyp());
        return ResponseEntity.ok(service.save(existing));
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        service.delete(documentId);
        return ResponseEntity.noContent().build();
    }
}
