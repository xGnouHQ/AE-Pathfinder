package com.pathfinder.dto.meinKonto;

import java.util.List;

public record PersonalDataResponse(
        Long id,
        String vorname,
        String nachname,
        String email,
        String studienrichtung,
        String jahrgang,
        String personalnummer,
        List<AbteilungDTO> praktika
) {}
