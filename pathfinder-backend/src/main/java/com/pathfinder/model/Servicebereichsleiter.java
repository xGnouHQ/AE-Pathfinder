package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.Data;
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

    private String bereich;           // z. B. "IT-Service", "Personal"
    private String kontaktperson;     // Name des Leiters
    private String email;
    private String telefonnummer;

    private LocalDateTime erstelltAm = LocalDateTime.now();

    // Beziehung zu Stellen: 1:n (ein Bereichsleiter hat mehrere Stellen)
    @OneToMany(mappedBy = "servicebereichsleiter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stelle> stellen = new ArrayList<>();
}
