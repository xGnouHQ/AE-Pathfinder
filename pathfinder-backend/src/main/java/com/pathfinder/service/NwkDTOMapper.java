package com.pathfinder.service;

import com.pathfinder.dto.meinKonto.*;
import com.pathfinder.model.Abteilung;
import com.pathfinder.model.Tag;
import com.pathfinder.model.Nachwuchskraft;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NwkDTOMapper {

    public TagDTO toTagDTO(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getName()
        );
    }

    public AbteilungDTO toAbteilungDTO(Abteilung abt) {
        return new AbteilungDTO(
                abt.getId(),
                abt.getName()
        );
    }

    public List<TagDTO> toTagDTOList(List<Tag> tags) {
        return tags.stream()
                .map(this::toTagDTO)
                .collect(Collectors.toList());
    }

    public List<AbteilungDTO> toAbteilungDTOList(List<Abteilung> abteilungen) {
        return abteilungen.stream()
                .map(this::toAbteilungDTO)
                .collect(Collectors.toList());
    }

    public PersonalDataResponse toPersonalDataDTO(Nachwuchskraft nwk) {
        return new PersonalDataResponse(
                nwk.getId(),
                nwk.getVorname(),
                nwk.getNachname(),
                nwk.getEmail(),
                nwk.getStudienrichtung(),
                nwk.getJahrgang(),
                nwk.getPersonalnummer(),
                toAbteilungDTOList(nwk.getPraktika())
        );
    }

    public ExperienceResponse toExperienceDTO(Nachwuchskraft nwk) {
        return new ExperienceResponse(
                nwk.getId(),
                toTagDTOList(nwk.getInteressen()),
                toAbteilungDTOList(nwk.getWunschabteilungen())
        );
    }
}
