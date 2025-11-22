package com.pathfinder.exception;

public class DuplicateBewerbungException extends RuntimeException {
    public DuplicateBewerbungException() {
        super("Sie haben sich bereits auf diese Stelle beworben.");
    }
}