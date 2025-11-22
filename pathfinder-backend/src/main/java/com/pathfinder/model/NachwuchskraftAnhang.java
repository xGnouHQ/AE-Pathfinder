package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "nachwuchskraft_anhang")
public class NachwuchskraftAnhang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Viele Anhänge gehören zu einer Nachwuchskraft
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nachwuchskraft_id", nullable = false)
    @JsonBackReference(value = "anhang-nwk")
    private Nachwuchskraft nachwuchskraft;

    // Relativer Pfad ab dem konfigurierten Upload-Root
    @Column(name = "DATEIPFAD", nullable = false, length = 500)
    private String dateipfad;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYP", nullable = false, length = 50)
    private DokumentTyp typ;

    @Column(name = "HOCHGELADEN_AM", nullable = false)
    private LocalDateTime hochgeladenAm = LocalDateTime.now();

    public enum DokumentTyp {
        LEBENSLAUF,
        MOTIVATIONSSCHREIBEN,
        ZEUGNIS,
        SONSTIGES
    }
}
