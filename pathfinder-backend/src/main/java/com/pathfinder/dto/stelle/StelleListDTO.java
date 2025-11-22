package com.pathfinder.dto.stelle;

import java.time.LocalDate;

public record StelleListDTO(
        Long id,
        String titel,
        String standort,
        String referat,
        String entgeltgruppe,
        String status,
        LocalDate bewerbungsfrist
) {}
