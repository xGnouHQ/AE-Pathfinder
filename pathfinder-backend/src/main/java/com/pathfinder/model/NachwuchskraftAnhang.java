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

    @ManyToOne
    @JoinColumn(name = "nachwuchskraft_id", nullable = false)
    @JsonBackReference
    private Nachwuchskraft nachwuchskraft;

    private String dateipfad;

    @Enumerated(EnumType.STRING)
    private DokumentTyp typ;

    private LocalDateTime hochgeladenAm = LocalDateTime.now();

    public enum DokumentTyp {
        LEBENSLAUF,
        MOTIVATIONSSCHREIBEN,
        ZEUGNIS,
        SONSTIGES
    }
}
