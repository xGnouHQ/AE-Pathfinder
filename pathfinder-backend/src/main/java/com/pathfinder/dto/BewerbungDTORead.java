package com.pathfinder.dto;

import java.time.LocalDateTime;

public record BewerbungDTORead(
        Long id,
        Long nachwuchskraftId,
        String nachwuchskraftName,
        Long stelleId,
        String stelleTitel,
        String status,
        LocalDateTime eingereichtAm,
        String kommentar
) {}
