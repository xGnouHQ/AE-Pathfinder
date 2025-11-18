package com.pathfinder.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long id) {
        super("Tag mit ID " + id + " wurde nicht gefunden");
    }
}
