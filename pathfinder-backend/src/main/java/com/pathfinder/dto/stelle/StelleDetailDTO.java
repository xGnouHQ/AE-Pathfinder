package com.pathfinder.dto.stelle;

import java.time.LocalDate;
import java.util.List;

public record StelleDetailDTO(
        Long id,
        String titel,
        String standort,
        String referat,
        String entgeltgruppe,
        String beschreibung,
        String status,
        LocalDate bewerbungsfrist,
        List<String> tags,
        String servicebereichsleiter
) {}
