package com.pathfinder.dto;

public record LoginRequestDTO(
        String email,
        String password
) {}