package com.pathfinder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentResponseDTO {
    private Long id;
    private String typ;
    private String dateipfad;
    private LocalDateTime hochgeladenAm;
    private Long nwkId;
}
