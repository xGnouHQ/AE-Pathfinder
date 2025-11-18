package com.pathfinder.controller;

import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.service.NachwuchskraftAnhangService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // POST /api/meinKonto/documents (PDF/DOCX Upload)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<NachwuchskraftAnhang> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nwkId") Long nwkId,
            @RequestParam("typ") NachwuchskraftAnhang.DokumentTyp typ
    ) {
        try {
            NachwuchskraftAnhang saved = service.storeFile(file, nwkId, typ);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/meinKonto/documents/{id}
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<NachwuchskraftAnhang> updateDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("typ") NachwuchskraftAnhang.DokumentTyp typ
    ) {
        try {
            NachwuchskraftAnhang saved = service.updateFile(id, file, typ);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /api/meinKonto/documents/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
