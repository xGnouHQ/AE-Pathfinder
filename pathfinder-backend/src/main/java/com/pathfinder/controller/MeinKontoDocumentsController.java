package com.pathfinder.controller;

import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.service.NachwuchskraftAnhangService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meinKonto/documents")
@CrossOrigin
public class MeinKontoDocumentsController {

    private final NachwuchskraftAnhangService service;

    public MeinKontoDocumentsController(NachwuchskraftAnhangService service) {
        this.service = service;
    }

    @GetMapping("/{nwkId}")
    public ResponseEntity<List<NachwuchskraftAnhang>> getDocuments(@PathVariable Long nwkId) {
        List<NachwuchskraftAnhang> docs = service.getByNachwuchskraft(nwkId);
        if (docs.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(docs);
    }

    // POST /api/meinKonto/documents
    @PostMapping
    public ResponseEntity<NachwuchskraftAnhang> uploadDocument(@RequestBody NachwuchskraftAnhang anhang) {
        if (!isValidFileType(anhang.getDateipfad())) {
            return ResponseEntity.badRequest().body(null);
        }
        NachwuchskraftAnhang saved = service.save(anhang);
        return ResponseEntity.ok(saved);
    }

    private boolean isValidFileType(String dateipfad) {
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
