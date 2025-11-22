package com.pathfinder.service;

import com.pathfinder.dto.stelle.StelleDetailDTO;
import com.pathfinder.dto.stelle.StelleListDTO;
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

    public StelleDetailDTO toDetailDTO(Stelle s) {
        return new StelleDetailDTO(
                s.getId(),
                s.getTitel(),
                s.getStandort(),
                s.getReferat(),
                s.getEntgeltgruppe(),
                s.getBeschreibung(),
                s.getStatus().name(),
                s.getBewerbungsfrist(),
                s.getTags().stream()
                        .map(t -> t.getName())
                        .collect(Collectors.toList()),
                s.getServicebereichsleiter().getKontaktperson()
                        + " (" + s.getServicebereichsleiter().getAbteilung() + ")"
        );
    }
}
