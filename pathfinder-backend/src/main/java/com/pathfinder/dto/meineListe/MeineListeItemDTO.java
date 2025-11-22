package com.pathfinder.dto.meineListe;

import java.time.LocalDateTime;
import java.util.List;

public record MeineListeItemDTO(
        Long stellenId,
        String titel,
        String standort,
        String referat,
        String entgeltgruppe,
        List<String> tags,
        LocalDateTime gemerktAm
) {}
