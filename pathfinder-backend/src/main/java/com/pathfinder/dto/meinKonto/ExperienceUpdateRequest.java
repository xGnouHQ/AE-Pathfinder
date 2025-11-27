package com.pathfinder.dto.meinKonto;

import java.util.List;

public record ExperienceUpdateRequest(
        List<Long> interessenIds,
        List<Long> wunschabteilungenIds,
        boolean programmieren,
        String programmiersprachen
) {}
