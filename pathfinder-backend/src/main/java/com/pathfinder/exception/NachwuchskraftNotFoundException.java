package com.pathfinder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NachwuchskraftNotFoundException extends RuntimeException {

    public NachwuchskraftNotFoundException(Long id) {
        super("Nachwuchskraft mit ID " + id + " wurde nicht gefunden.");
    }
}
