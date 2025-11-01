package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "servicebereichsleiter_id", nullable = false)
    @JsonBackReference("sbl-stellen")
    private Servicebereichsleiter servicebereichsleiter;

    @OneToMany(mappedBy = "stelle", cascade = CascadeType.ALL)
    @JsonManagedReference("bewerbung-stelle")
    private List<Bewerbung> bewerbungen = new ArrayList<>();
}
