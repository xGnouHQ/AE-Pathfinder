package com.pathfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocumentListResponseDTO {
    private Long nwkId;
    private List<DocumentResponseDTO> documents;
}
