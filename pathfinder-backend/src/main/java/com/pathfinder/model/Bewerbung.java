package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference("bewerbung-nwk")
    private Nachwuchskraft nachwuchskraft;

    // Beziehung zur Stelle (n:1)
    @ManyToOne
    @JoinColumn(name = "stelle_id", nullable = false)
    @JsonBackReference("bewerbung-stelle")
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
