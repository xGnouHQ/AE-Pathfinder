package com.pathfinder.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super("Keine Nachwuchskraft mit E-Mail gefunden: " + email);
    }
}
