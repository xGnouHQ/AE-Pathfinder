package com.pathfinder.service;

import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.repository.NachwuchskraftAnhangRepository;
import com.pathfinder.repository.NachwuchskraftRepository;
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

    public NachwuchskraftAnhangService(NachwuchskraftAnhangRepository repository,
                                       NachwuchskraftRepository nwkRepository) {
        this.repository = repository;
        this.nwkRepository = nwkRepository;
    }

    public List<NachwuchskraftAnhang> getByNachwuchskraft(Long id) {
        return repository.findByNachwuchskraftId(id);
    }

    // Datei speichern + Datensatz anlegen
    public NachwuchskraftAnhang storeFile(MultipartFile file, Long nwkId,
                                          NachwuchskraftAnhang.DokumentTyp typ)
            throws IOException {

        Nachwuchskraft nwk = nwkRepository.findById(nwkId).orElseThrow();

        Path uploadDir = Paths.get("uploads/documents/" + nwkId);
        Files.createDirectories(uploadDir);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);

        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE_NEW);

        NachwuchskraftAnhang anhang = new NachwuchskraftAnhang();
        anhang.setNachwuchskraft(nwk);
        anhang.setTyp(typ);
        anhang.setDateipfad(filePath.toString());
        anhang.setHochgeladenAm(LocalDateTime.now());

        return repository.save(anhang);
    }

    // Datei ersetzen + Datensatz aktualisieren
    public NachwuchskraftAnhang updateFile(Long id, MultipartFile file,
                                           NachwuchskraftAnhang.DokumentTyp typ)
            throws IOException {

        NachwuchskraftAnhang existing = repository.findById(id).orElseThrow();

        Path oldPath = Paths.get(existing.getDateipfad());
        if (Files.exists(oldPath)) {
            Files.delete(oldPath);
        }

        Path uploadDir = Paths.get("uploads/documents/" + existing.getNachwuchskraft().getId());
        Files.createDirectories(uploadDir);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);

        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE_NEW);

        existing.setTyp(typ);
        existing.setDateipfad(filePath.toString());
        existing.setHochgeladenAm(LocalDateTime.now());

        return repository.save(existing);
    }

    public void delete(Long id) {
        NachwuchskraftAnhang existing = repository.findById(id).orElseThrow();
        Path filePath = Paths.get(existing.getDateipfad());

        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException ignored) {}
        }

        repository.deleteById(id);
    }
}
