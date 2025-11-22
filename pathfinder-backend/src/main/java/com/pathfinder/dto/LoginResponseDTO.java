package com.pathfinder.dto;

public record LoginResponseDTO(
        Long id,
        String vorname,
        String nachname,
        String email,
        String jahrgang
) {}
