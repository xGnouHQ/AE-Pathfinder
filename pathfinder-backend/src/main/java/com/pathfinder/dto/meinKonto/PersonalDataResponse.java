package com.pathfinder.dto.meinKonto;

import com.pathfinder.model.Abteilung;
import java.util.List;

public record PersonalDataResponse(
        Long id,
        String vorname,
        String nachname,
        String email,
        String studienrichtung,
        String jahrgang,
        List<Abteilung> praktika
) {}
