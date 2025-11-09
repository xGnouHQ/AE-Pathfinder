package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "abteilung")
public class Abteilung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // z.B. "IT@M - IT-Dienstleistungen"
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    // Rückverweis auf interessierte Nachwuchskräfte (nur intern, nicht notwendig fürs JSON)
    @ManyToMany(mappedBy = "wunschabteilungen")
    @JsonIgnore
    private List<Nachwuchskraft> interessierteNachwuchskraefte = new ArrayList<>();
}
