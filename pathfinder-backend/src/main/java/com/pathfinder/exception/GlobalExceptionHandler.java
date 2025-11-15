package com.pathfinder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFound(EmailNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", 404,
                        "error", "EMAIL_NOT_FOUND",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> handleWrongPassword(WrongPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "status", 401,
                        "error", "WRONG_PASSWORD",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DuplicateBewerbungException.class)
    public ResponseEntity<?> handleDuplicateBewerbung(DuplicateBewerbungException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)  // 409
                .body(Map.of(
                        "status", 409,
                        "error", "DUPLICATE_BEWERBUNG",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(NachwuchskraftNotFoundException.class)
    public ResponseEntity<?> handleNwkNotFound(NachwuchskraftNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "error", "NACHWUCHSKRAFT_NOT_FOUND",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }

    @ExceptionHandler(StelleNotFoundException.class)
    public ResponseEntity<?> handleStelleNotFound(StelleNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "error", "STELLE_NOT_FOUND",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }

    @ExceptionHandler(BewerbungNotFoundException.class)
    public ResponseEntity<?> handleBewerbungNotFound(BewerbungNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "error", "BEWERBUNG_NOT_FOUND",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }

    @ExceptionHandler(InvalidBewerbungDataException.class)
    public ResponseEntity<?> handleInvalidData(InvalidBewerbungDataException ex) {
        return ResponseEntity.status(400).body(Map.of(
                "status", 400,
                "error", "INVALID_BEWERBUNG_DATA",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<?> handleTagNotFound(TagNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "error", "TAG_NOT_FOUND",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }

    @ExceptionHandler(AbteilungNotFoundException.class)
    public ResponseEntity<?> handleAbtNotFound(AbteilungNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "error", "ABTEILUNG_NOT_FOUND",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }
}
