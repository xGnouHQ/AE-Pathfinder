package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class StellenmerklisteId implements Serializable {
    private Long nachwuchskraftId;
    private Long stelleId;
}
