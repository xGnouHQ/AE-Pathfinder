package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "servicebereichsleiter")
public class Servicebereichsleiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "abteilung_id", nullable = false)
    @ToString.Exclude
    private Abteilung abteilung;

    private String kontaktperson;
    private String email;
    private String telefonnummer;

    private LocalDateTime erstelltAm = LocalDateTime.now();

    @OneToMany(mappedBy = "servicebereichsleiter")
    @JsonIgnore
    @ToString.Exclude
    private List<Stelle> stellen = new ArrayList<>();
}