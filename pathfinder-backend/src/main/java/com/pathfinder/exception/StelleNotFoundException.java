package com.pathfinder.exception;

public class StelleNotFoundException extends RuntimeException {
    public StelleNotFoundException(Long id) {
        super("Stelle mit ID " + id + " wurde nicht gefunden");
    }
}
