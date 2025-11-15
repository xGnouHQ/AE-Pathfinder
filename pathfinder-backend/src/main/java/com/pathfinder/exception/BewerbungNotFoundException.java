package com.pathfinder.exception;

public class BewerbungNotFoundException extends RuntimeException {
    public BewerbungNotFoundException(Long id) {
        super("Bewerbung mit ID " + id + " wurde nicht gefunden");
    }
}
