package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bewerbung")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bewerbung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID wird automatisch generiert
    private Long id;

    // Beziehung zur Nachwuchskraft (n:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nachwuchskraft_id", nullable = false)
    @JsonBackReference("bewerbung-nwk")
    private Nachwuchskraft nachwuchskraft;

    // Beziehung zur Stelle (n:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stelle_id", nullable = false)
    @JsonIgnoreProperties({"bewerbungen","servicebereichsleiter","tags","hibernateLazyInitializer","handler"})
    private Stelle stelle;

    @Enumerated(EnumType.STRING)
    private Status status = Status.EINGEREICHT;

    private LocalDateTime eingereichtAm = LocalDateTime.now();

    private String kommentar;

    public enum Status {
        EINGEREICHT,
        IN_PRUEFUNG,
        ABGELEHNT,
        ANGELADEN,
        ANGENOMMEN
    }

    // Konstruktor für einfache Erstellung
    public Bewerbung(Nachwuchskraft nwk, Stelle stelle, String kommentar) {
        this.nachwuchskraft = nwk;
        this.stelle = stelle;
        this.kommentar = kommentar;
        this.status = Status.EINGEREICHT;
        this.eingereichtAm = LocalDateTime.now();
    }
}
