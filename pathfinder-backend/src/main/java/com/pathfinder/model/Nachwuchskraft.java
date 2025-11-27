package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "nachwuchskraft")
public class Nachwuchskraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERSONALNUMMER", unique = true)
    private String personalnummer;

    @Column(name = "VORNAME")
    private String vorname;

    @Column(name = "NACHNAME")
    private String nachname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STUDIENRICHTUNG")
    private String studienrichtung;

    @Column(name = "JAHRGANG", nullable = false, length = 20)
    private String jahrgang;

    @Column(name = "ERSTELLT_AM")
    private LocalDateTime erstelltAm = LocalDateTime.now();

    @Column(name = "PROGRAMMIEREN", nullable = false)
    private boolean programmieren = false;

    @Column(name = "PROGRAMMIERSPRACHEN")
    private String programmiersprachen;

    // Interessen (Tags)
    @ManyToMany
    @JoinTable(
            name = "nachwuchskraft_interesse",
            joinColumns = @JoinColumn(name = "nachwuchskraft_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> interessen = new ArrayList<>();

    // Absolvierte Praktika
    @ManyToMany
    @JoinTable(
            name = "nachwuchskraft_praktikum",
            joinColumns = @JoinColumn(name = "nachwuchskraft_id"),
            inverseJoinColumns = @JoinColumn(name = "abteilung_id")
    )
    private List<Abteilung> praktika = new ArrayList<>();

    // Anhänge 1:n
    @OneToMany(mappedBy = "nachwuchskraft", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "anhang-nwk")
    private List<NachwuchskraftAnhang> anhaenge = new ArrayList<>();

    // Bewerbungen 1:n
    @OneToMany(mappedBy = "nachwuchskraft", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("bewerbung-nwk")
    private List<Bewerbung> bewerbungen = new ArrayList<>();

    // Wunschabteilungen
    @ManyToMany
    @JoinTable(
            name = "wunschabteilung",
            joinColumns = @JoinColumn(name = "nachwuchskraft_id"),
            inverseJoinColumns = @JoinColumn(name = "abteilung_id")
    )
    private List<Abteilung> wunschabteilungen = new ArrayList<>();
}
