package com.pathfinder.service;

import com.pathfinder.exception.FileTooLargeException;
import com.pathfinder.exception.InvalidFileTypeException;
import com.pathfinder.exception.NachwuchskraftNotFoundException;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.repository.NachwuchskraftAnhangRepository;
import com.pathfinder.repository.NachwuchskraftRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NachwuchskraftAnhangService {

    private final NachwuchskraftAnhangRepository repository;
    private final NachwuchskraftRepository nwkRepository;
    private final Path rootDir;

    public NachwuchskraftAnhangService(NachwuchskraftAnhangRepository repository,
                                       NachwuchskraftRepository nwkRepository,
                                       @Value("${pathfinder.upload-dir:uploads}") String uploadDir) {
        this.repository = repository;
        this.nwkRepository = nwkRepository;
        this.rootDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public List<NachwuchskraftAnhang> getByNachwuchskraft(Long id) {
        return repository.findByNachwuchskraftId(id);
    }

    // Datei speichern + Datensatz anlegen
    public NachwuchskraftAnhang storeFile(MultipartFile file, Long nwkId,
                                          NachwuchskraftAnhang.DokumentTyp typ)
            throws IOException {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileTypeException("Leere Datei kann nicht hochgeladen werden.");
        }

        Nachwuchskraft nwk = nwkRepository.findById(nwkId)
                .orElseThrow(() -> new NachwuchskraftNotFoundException(nwkId));

        validateFileType(file);
        validateFileSize(file);

        // Unterordner: documents/{nwkId}
        Path uploadDir = rootDir.resolve("documents").resolve(nwkId.toString());
        Files.createDirectories(uploadDir);

        String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "upload.bin";

        String filename = System.currentTimeMillis() + "_" + originalName;

        Path target = uploadDir.resolve(filename);

        // Physisch speichern
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // relativer Pfad ab rootDir
        String relativePath = rootDir.relativize(target).toString().replace("\\", "/");

        NachwuchskraftAnhang anhang = new NachwuchskraftAnhang();
        anhang.setNachwuchskraft(nwk);
        anhang.setTyp(typ);
        anhang.setDateipfad(relativePath);
        anhang.setHochgeladenAm(LocalDateTime.now());

        return repository.save(anhang);
    }

    // Datei ersetzen + Datensatz aktualisieren
    public NachwuchskraftAnhang updateFile(Long id, MultipartFile file,
                                           NachwuchskraftAnhang.DokumentTyp typ)
            throws IOException {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileTypeException("Leere Datei kann nicht hochgeladen werden.");
        }

        NachwuchskraftAnhang existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Anhang mit ID " + id + " nicht gefunden."));

        validateFileType(file);
        validateFileSize(file);

        if (existing.getDateipfad() != null) {
            Path oldPath = rootDir.resolve(existing.getDateipfad()).normalize();
            if (Files.exists(oldPath)) {
                Files.delete(oldPath);
            }
        }

        Long nwkId = existing.getNachwuchskraft().getId();
        Path uploadDir = rootDir.resolve("documents").resolve(nwkId.toString());
        Files.createDirectories(uploadDir);

        String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "upload.bin";

        String filename = System.currentTimeMillis() + "_" + originalName;

        Path target = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        String relativePath = rootDir.relativize(target).toString().replace("\\", "/");

        existing.setTyp(typ);
        existing.setDateipfad(relativePath);
        existing.setHochgeladenAm(LocalDateTime.now());

        return repository.save(existing);
    }

    public void delete(Long id) {
        NachwuchskraftAnhang existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Anhang mit ID " + id + " nicht gefunden."));

        if (existing.getDateipfad() != null) {
            Path filePath = rootDir.resolve(existing.getDateipfad()).normalize();
            if (Files.exists(filePath)) {
                try {
                    Files.delete(filePath);
                } catch (IOException ignored) {
                }
            }
        }

        repository.deleteById(id);
    }

    private void validateFileType(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name == null) {
            throw new InvalidFileTypeException("Dateiname fehlt.");
        }

        String lower = name.toLowerCase();

        if (!(lower.endsWith(".pdf") || lower.endsWith(".docx") || lower.endsWith(".doc"))) {
            throw new InvalidFileTypeException("Ungültiges Dateiformat. Erlaubt sind nur PDF oder DOC/DOCX.");
        }
    }

    private void validateFileSize(MultipartFile file) {
        long maxBytes = 10 * 1024 * 1024; // 10 MB

        if (file.getSize() > maxBytes) {
            throw new FileTooLargeException("Die Datei ist zu groß. Maximal erlaubt sind 10 MB.");
        }
    }
}
