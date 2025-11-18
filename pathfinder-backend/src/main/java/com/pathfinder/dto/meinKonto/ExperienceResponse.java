package com.pathfinder.dto.meinKonto;

import com.pathfinder.model.Abteilung;
import com.pathfinder.model.Tag;
import java.util.List;

public record ExperienceResponse(
        Long id,
        List<Tag> interessen,
        List<Abteilung> wunschabteilungen
) {}
