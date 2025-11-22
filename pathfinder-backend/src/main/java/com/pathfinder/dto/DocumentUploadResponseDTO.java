package com.pathfinder.dto;

import lombok.Data;

@Data
public class DocumentUploadResponseDTO {
    private Long id;
    private String message;
    private String typ;
    private String fileName;
    private Long nwkId;
}
