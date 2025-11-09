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

    // neu: Jahrgang als String, z.B. "2023/2026"
    @Column(name = "JAHRGANG", nullable = false, length = 20)
    private String jahrgang;

    @Column(name = "ERSTELLT_AM")
    private LocalDateTime erstelltAm = LocalDateTime.now();

    // vom Nachwuchskraft selbst bearbeitbar
    @Column(name = "INTERESSEN", length = 1000)
    private String interessen;

    // umbenannt: Erfahrungen -> Praktika
    // nicht mehr über /meinKonto von NWK bearbeitbar
    @Column(name = "PRAKTIKA", length = 2000)
    private String praktika;

    // Beziehung zu Anhaenge (1:n)
    @OneToMany(mappedBy = "nachwuchskraft", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NachwuchskraftAnhang> anhaenge = new ArrayList<>();

    // Beziehung zu Bewerbung (1:n)
    @OneToMany(mappedBy = "nachwuchskraft", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("bewerbung-nwk")
    private List<Bewerbung> bewerbungen = new ArrayList<>();

    // Wunschabteilungen (n:m)
    @ManyToMany
    @JoinTable(
            name = "wunschabteilung",
            joinColumns = @JoinColumn(name = "nachwuchskraft_id"),
            inverseJoinColumns = @JoinColumn(name = "abteilung_id")
    )
    private List<Abteilung> wunschabteilungen = new ArrayList<>();
}
