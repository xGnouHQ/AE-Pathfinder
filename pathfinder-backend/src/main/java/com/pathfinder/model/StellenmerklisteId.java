package com.pathfinder.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StellenmerklisteId implements Serializable {
    private Long nachwuchskraftId;
    private Long stelleId;
}
