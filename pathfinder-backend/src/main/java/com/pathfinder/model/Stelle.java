package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "stelle")
public class Stelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;
    private String standort;

    @Column(length = 2000)
    private String beschreibung;

    @ElementCollection
    @CollectionTable(name = "stelle_tags", joinColumns = @JoinColumn(name = "stelle_id"))
    @Column(name = "tag")
    private List<String> tags;  // z. B. ["Java", "Spring Boot", "Teamarbeit"]

    @Enumerated(EnumType.STRING)
    private Status status = Status.OFFEN;

    private LocalDate bewerbungsfrist;

    public enum Status {
        OFFEN,
        GESCHLOSSEN
    }

    @ManyToOne
    @JoinColumn(name = "servicebereichsleiter_id", nullable = false)
    private Servicebereichsleiter servicebereichsleiter;
}
