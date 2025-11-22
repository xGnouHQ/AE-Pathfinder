package com.pathfinder.service;

import com.pathfinder.dto.meineListe.MeineListeItemDTO;
import com.pathfinder.model.Stellenmerkliste;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MeineListeMapper {

    public MeineListeItemDTO toDTO(Stellenmerkliste entry) {
        return new MeineListeItemDTO(
                entry.getStelle().getId(),
                entry.getStelle().getTitel(),
                entry.getStelle().getStandort(),
                entry.getStelle().getReferat(),
                entry.getStelle().getEntgeltgruppe(),
                entry.getStelle().getTags().stream()
                        .map(t -> t.getName())
                        .collect(Collectors.toList()),
                entry.getErstelltAm()
        );
    }
}
