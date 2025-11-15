package com.pathfinder.exception;

public class NachwuchskraftNotFoundException extends RuntimeException {
    public NachwuchskraftNotFoundException(Long id) {
        super("Nachwuchskraft mit ID " + id + " wurde nicht gefunden");
    }
}