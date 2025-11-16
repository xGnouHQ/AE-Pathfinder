package com.pathfinder.service;

import com.pathfinder.model.Bewerbung;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.repository.NachwuchskraftAnhangRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NachwuchskraftAnhangService {

    private final NachwuchskraftAnhangRepository repository;
    private final NachwuchskraftService nwkService;

    public NachwuchskraftAnhangService(NachwuchskraftAnhangRepository repository,
                                       NachwuchskraftService nwkService) {
        this.repository = repository;
        this.nwkService = nwkService;
    }

    // Alle Anhänge einer Nachwuchskraft abrufen
    public List<NachwuchskraftAnhang> getByNachwuchskraft(Long nwkId) {
        return repository.findByNachwuchskraftId(nwkId);
    }

    // Einen Anhang anhand seiner ID abrufen
    public NachwuchskraftAnhang getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anhang mit ID " + id + " nicht gefunden"));
    }

    // Anhang speichern (bereits existierendes Objekt)
    public NachwuchskraftAnhang save(NachwuchskraftAnhang anhang) {
        return repository.save(anhang);
    }

    public NachwuchskraftAnhang saveFile(Long nwkId, MultipartFile file) throws IOException {
        Nachwuchskraft nwk = nwkService.getById(nwkId);

        if (nwk == null) throw new IllegalArgumentException("Nachwuchskraft nicht gefunden");

        String uploadDir = "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filePath = uploadDir + "/" + file.getOriginalFilename();
        File dest = new File(filePath);
        file.transferTo(dest);

        NachwuchskraftAnhang anhang = new NachwuchskraftAnhang();
        anhang.setNachwuchskraft(nwk);
        anhang.setDateipfad(filePath);

        // Enum korrekt setzen
        anhang.setTyp(NachwuchskraftAnhang.DokumentTyp.SONSTIGES);

        return repository.save(anhang);
    }


    // Anhänge einer Bewerbung zuordnen
    public void assignFilesToBewerbung(Bewerbung bewerbung, List<Long> fileIds) {
        if (fileIds == null || fileIds.isEmpty()) return;

        List<NachwuchskraftAnhang> attachments = new ArrayList<>();
        for (Long id : fileIds) {
            NachwuchskraftAnhang anhang = getById(id);
            anhang.setBewerbung(bewerbung);
            attachments.add(anhang);
        }

        bewerbung.getAnhange().addAll(attachments);
        attachments.forEach(repository::save);
    }

    // Anhang löschen
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
