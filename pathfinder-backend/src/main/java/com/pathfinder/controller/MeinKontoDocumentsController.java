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

    // GET /api/meinKonto/documents/{nwkId}
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

    // PUT /api/meinKonto/documents/{nwkId}
    @PutMapping("/{nwkId}")
    public ResponseEntity<NachwuchskraftAnhang> updateDocument(
            @PathVariable Long id,
            @RequestBody NachwuchskraftAnhang updated) {
        if (!isValidFileType(updated.getDateipfad())) {
            return ResponseEntity.badRequest().build();
        }
        NachwuchskraftAnhang saved = service.update(id, updated);
        return ResponseEntity.ok(saved);
    }

    // DELETE /api/meinKonto/documents/{nwkId}
    @DeleteMapping("/{nwkId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean isValidFileType(String path) {
        if (path == null) return false;
        return path.toLowerCase().endsWith(".pdf") || path.toLowerCase().endsWith(".docx");
    }
}
