package com.pathfinder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class FileTooLargeException extends RuntimeException {

    public FileTooLargeException(String message) {
        super(message);
    }
}
