package com.pathfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "servicebereichsleiter")
    @JsonIgnore
    private List<Stelle> stellen = new ArrayList<>();
}
