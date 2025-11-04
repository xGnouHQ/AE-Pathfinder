package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stellenmerkliste")
public class Stellenmerkliste {

    @EmbeddedId
    private StellenmerklisteId id;

    @ManyToOne
    @MapsId("nachwuchskraftId")
    @JoinColumn(name = "nachwuchskraft_id")
    private Nachwuchskraft nachwuchskraft;

    @ManyToOne
    @MapsId("stelleId")
    @JoinColumn(name = "stelle_id")
    private Stelle stelle;

    @Column(name = "merken_status", nullable = false)
    private Boolean merkenStatus = true;

    @Column(name = "erstellt_am", nullable = false)
    private LocalDateTime erstelltAm = LocalDateTime.now();
}
