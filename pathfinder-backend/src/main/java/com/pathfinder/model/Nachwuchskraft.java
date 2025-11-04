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

    @Column(name = "EINTRITTSJAHR", nullable = false)
    private int eintrittsjahr;

    @Column(name = "ERSTELLT_AM")
    private LocalDateTime erstelltAm = LocalDateTime.now();

    //vom Nachwuchskraft selbst bearbeitbar
    @Column(name = "INTERESSEN", length = 1000)
    private String interessen;

    @Column(name = "ERFAHRUNGEN", length = 2000)
    private String erfahrungen;

    // Beziehung zu Anhaenge (1:n)
    @OneToMany(mappedBy = "nachwuchskraft", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NachwuchskraftAnhang> anhaenge = new ArrayList<>();

    // Beziehung zu Bewerbung
    @OneToMany(mappedBy = "nachwuchskraft", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("bewerbung-nwk")
    private List<Bewerbung> bewerbungen = new ArrayList<>();
}
