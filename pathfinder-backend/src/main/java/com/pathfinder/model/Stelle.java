package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "stelle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;
    private String standort;
    private String referat;
    private String entgeltgruppe;

    @Column(length = 2000)
    private String beschreibung;

    @ManyToMany
    @JoinTable(
            name = "stelle_tag",
            joinColumns = @JoinColumn(name = "stelle_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status = Status.OFFEN;

    private LocalDate bewerbungsfrist;

    public enum Status {
        OFFEN,
        GESCHLOSSEN
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicebereichsleiter_id", nullable = false)
    @JsonIgnoreProperties({"stellen"})
    private Servicebereichsleiter servicebereichsleiter;

    @OneToMany(mappedBy = "stelle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Bewerbung> bewerbungen = new ArrayList<>();
}
