package com.pathfinder.service;

import com.pathfinder.dto.BewerbungDTORead;
import com.pathfinder.dto.BewerbungDTOWrite;
import com.pathfinder.exception.*;
import com.pathfinder.model.Bewerbung;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.model.Stelle;
import com.pathfinder.repository.BewerbungRepository;
import com.pathfinder.repository.NachwuchskraftRepository;
import com.pathfinder.repository.StelleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BewerbungService {

    private final BewerbungRepository repository;
    private final NachwuchskraftRepository nwkRepository;
    private final StelleRepository stelleRepository;

    public BewerbungService(BewerbungRepository repository,
                            NachwuchskraftRepository nwkRepository,
                            StelleRepository stelleRepository) {
        this.repository = repository;
        this.nwkRepository = nwkRepository;
        this.stelleRepository = stelleRepository;
    }


    // -----------------------------
    // CREATE
    // -----------------------------
    public BewerbungDTORead create(BewerbungDTOWrite dto) {

        if (dto.nachwuchskraftId() == null)
            throw new InvalidBewerbungDataException("Nachwuchskraft-ID darf nicht null sein");

        if (dto.stelleId() == null)
            throw new InvalidBewerbungDataException("Stelle-ID darf nicht null sein");

        if (repository.existsByNachwuchskraftIdAndStelleId(dto.nachwuchskraftId(), dto.stelleId()))
            throw new DuplicateBewerbungException();

        Nachwuchskraft nwk = nwkRepository.findById(dto.nachwuchskraftId())
                .orElseThrow(() -> new NachwuchskraftNotFoundException(dto.nachwuchskraftId()));

        Stelle stelle = stelleRepository.findById(dto.stelleId())
                .orElseThrow(() -> new StelleNotFoundException(dto.stelleId()));

        Bewerbung b = new Bewerbung();
        b.setNachwuchskraft(nwk);
        b.setStelle(stelle);
        b.setKommentar(dto.kommentar());

        return toDTO(repository.save(b));
    }


    // -----------------------------
    // UPDATE
    // -----------------------------
    public BewerbungDTORead update(Long id, BewerbungDTOWrite dto) {

        Bewerbung existing = repository.findById(id)
                .orElseThrow(() -> new BewerbungNotFoundException(id));

        Nachwuchskraft nwk = nwkRepository.findById(dto.nachwuchskraftId())
                .orElseThrow(() -> new NachwuchskraftNotFoundException(dto.nachwuchskraftId()));

        Stelle stelle = stelleRepository.findById(dto.stelleId())
                .orElseThrow(() -> new StelleNotFoundException(dto.stelleId()));

        existing.setNachwuchskraft(nwk);
        existing.setStelle(stelle);
        existing.setKommentar(dto.kommentar());

        return toDTO(repository.save(existing));
    }


    // -----------------------------
    // GET single
    // -----------------------------
    public BewerbungDTORead getDTOById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BewerbungNotFoundException(id));
    }


    // -----------------------------
    // GET all
    // -----------------------------
    public List<BewerbungDTORead> getAllDTO() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }


    // -----------------------------
    // GET by Nachwuchskraft
    // -----------------------------
    public List<BewerbungDTORead> getByNachwuchskraftDTO(Long nwkId) {
        return repository.findByNachwuchskraftId(nwkId).stream()
                .map(this::toDTO)
                .toList();
    }


    // -----------------------------
    // GET by Stelle
    // -----------------------------
    public List<BewerbungDTORead> getByStelleDTO(Long stelleId) {
        return repository.findByStelleId(stelleId).stream()
                .map(this::toDTO)
                .toList();
    }


    // -----------------------------
    // DELETE
    // -----------------------------
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new BewerbungNotFoundException(id);

        repository.deleteById(id);
    }


    // -----------------------------
    // MAPPING ENTITY → DTO
    // -----------------------------
    private BewerbungDTORead toDTO(Bewerbung b) {
        return new BewerbungDTORead(
                b.getId(),
                b.getNachwuchskraft().getId(),
                b.getNachwuchskraft().getVorname() + " " + b.getNachwuchskraft().getNachname(),
                b.getStelle().getId(),
                b.getStelle().getTitel(),
                b.getStatus().name(),
                b.getEingereichtAm(),
                b.getKommentar()
        );
    }
}
