package com.pathfinder.dto.stelle;

import com.pathfinder.model.Servicebereichsleiter;
import com.pathfinder.model.Stelle;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StelleDTOMapper {

    public StelleListDTO toListDTO(Stelle s) {
        return new StelleListDTO(
                s.getId(),
                s.getTitel(),
                s.getStandort(),
                s.getReferat(),
                s.getEntgeltgruppe(),
                s.getStatus().name(),
                s.getBewerbungsfrist()
        );
    }

    // Mapping für Detailansicht
    public StelleDetailDTO toDetailDTO(Stelle s) {
        Servicebereichsleiter sbl = s.getServicebereichsleiter();
        String kontaktInfo = null;

        if (sbl != null) {
            kontaktInfo = sbl.getKontaktperson()
                    + (sbl.getEmail() != null ? " | " + sbl.getEmail() : "")
                    + (sbl.getTelefonnummer() != null ? " | " + sbl.getTelefonnummer() : "");
        }

        return new StelleDetailDTO(
                s.getId(),
                s.getTitel(),
                s.getStandort(),
                s.getReferat(),
                s.getEntgeltgruppe(),
                s.getBeschreibung(),
                s.getStatus().name(),
                s.getBewerbungsfrist(),
                s.getTags().stream().map(t -> t.getName()).toList(),
                kontaktInfo
        );
    }
}
