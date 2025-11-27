package com.pathfinder.dto.meinKonto;

import java.util.List;

public record OptionsResponse(
        List<TagDTO> tags,
        List<AbteilungDTO> abteilungen
) {}
