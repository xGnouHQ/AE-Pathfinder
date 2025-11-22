package com.pathfinder.dto;

import com.pathfinder.model.NachwuchskraftAnhang;

public class DocumentMapper {

    public static DocumentResponseDTO toDTO(NachwuchskraftAnhang entity) {
        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setId(entity.getId());
        dto.setTyp(entity.getTyp().name());
        dto.setDateipfad(entity.getDateipfad());
        dto.setHochgeladenAm(entity.getHochgeladenAm());
        dto.setNwkId(entity.getNachwuchskraft().getId());
        return dto;
    }

    public static DocumentUploadResponseDTO toUploadDTO(NachwuchskraftAnhang entity) {
        DocumentUploadResponseDTO dto = new DocumentUploadResponseDTO();
        dto.setId(entity.getId());
        dto.setMessage("Upload erfolgreich");
        dto.setTyp(entity.getTyp().name());
        dto.setFileName(entity.getDateipfad());
        dto.setNwkId(entity.getNachwuchskraft().getId());
        return dto;
    }
}
