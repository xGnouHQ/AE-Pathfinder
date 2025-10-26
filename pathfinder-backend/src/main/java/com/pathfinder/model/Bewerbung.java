package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bewerbung")
public class Bewerbung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Beziehung zur Nachwuchskraft (n:1)
    @ManyToOne
    @JoinColumn(name = "nachwuchskraft_id", nullable = false)
    private Nachwuchskraft nachwuchskraft;

    // Beziehung zur Stelle (n:1)
    @ManyToOne
    @JoinColumn(name = "stelle_id", nullable = false)
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
}
