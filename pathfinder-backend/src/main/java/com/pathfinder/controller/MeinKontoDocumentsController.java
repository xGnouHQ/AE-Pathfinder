package com.pathfinder.controller;

import com.pathfinder.dto.DocumentListResponseDTO;
import com.pathfinder.dto.DocumentResponseDTO;
import com.pathfinder.dto.DocumentUploadResponseDTO;
import com.pathfinder.dto.DocumentMapper;
import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.service.NachwuchskraftAnhangService;
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

    public MeinKontoDocumentsController(NachwuchskraftAnhangService service) {
        this.service = service;
    }

    // GET /api/meinKonto/documents/{nwkId}
    @GetMapping("/{nwkId}")
    public ResponseEntity<DocumentListResponseDTO> getDocuments(@PathVariable Long nwkId) {
        List<NachwuchskraftAnhang> docs = service.getByNachwuchskraft(nwkId);

        if (docs.isEmpty())
            return ResponseEntity.noContent().build();

        List<DocumentResponseDTO> dtos = docs.stream()
                .map(DocumentMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new DocumentListResponseDTO(nwkId, dtos));
    }

    // POST (Upload)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DocumentUploadResponseDTO> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nwkId") Long nwkId,
            @RequestParam("typ") NachwuchskraftAnhang.DokumentTyp typ
    ) throws IOException {
        NachwuchskraftAnhang saved = service.storeFile(file, nwkId, typ);
        return ResponseEntity.ok(DocumentMapper.toUploadDTO(saved));
    }

    // PUT (Update)
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<DocumentUploadResponseDTO> updateDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("typ") NachwuchskraftAnhang.DokumentTyp typ
    ) throws IOException {
        NachwuchskraftAnhang saved = service.updateFile(id, file, typ);
        return ResponseEntity.ok(DocumentMapper.toUploadDTO(saved));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
