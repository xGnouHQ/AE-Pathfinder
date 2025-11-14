package com.pathfinder.exception;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Falsches Passwort");
    }
}
