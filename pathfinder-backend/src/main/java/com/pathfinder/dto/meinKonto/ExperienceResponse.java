package com.pathfinder.dto.meinKonto;

import java.util.List;

public record ExperienceResponse(
        Long id,
        List<TagDTO> interessen,
        List<AbteilungDTO> wunschabteilungen
) {}
