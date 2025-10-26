package com.pathfinder.model;

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
    private Long id;

    @Column(unique = true)
    private String personalnummer;

    private String vorname;
    private String nachname;
    private String email;
    private String studienrichtung;
    private int eintrittsjahr;

    private LocalDateTime erstelltAm = LocalDateTime.now();

    // Beziehung zu Anhaengen (1:n)
    @OneToMany(mappedBy = "nachwuchskraft", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NachwuchskraftAnhang> anhaenge = new ArrayList<>();
}
