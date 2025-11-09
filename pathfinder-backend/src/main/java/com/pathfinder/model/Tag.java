package com.pathfinder.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // z. B. "Java", "Spring Boot", "Teamarbeit"

    @ManyToMany(mappedBy = "interessen")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Nachwuchskraft> nachwuchskraefte = new ArrayList<>();
}
