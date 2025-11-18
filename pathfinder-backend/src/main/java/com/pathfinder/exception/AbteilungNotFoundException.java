package com.pathfinder.exception;

public class AbteilungNotFoundException extends RuntimeException {
    public AbteilungNotFoundException(Long id) {
        super("Abteilung mit ID " + id + " wurde nicht gefunden");
    }
}
