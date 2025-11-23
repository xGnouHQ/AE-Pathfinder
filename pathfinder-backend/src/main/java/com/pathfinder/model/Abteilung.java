package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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

    @OneToOne(mappedBy = "abteilung")
    @ToString.Exclude   // <<< verhindert rekursives toString()
    private Servicebereichsleiter servicebereichsleiter;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "wunschabteilungen")
    @JsonIgnore
    @ToString.Exclude
    private List<Nachwuchskraft> interessierteNachwuchskraefte = new ArrayList<>();

    @ManyToMany(mappedBy = "praktika")
    @JsonIgnore
    @ToString.Exclude
    private List<Nachwuchskraft> praktikumNachwuchskraefte = new ArrayList<>();
}
