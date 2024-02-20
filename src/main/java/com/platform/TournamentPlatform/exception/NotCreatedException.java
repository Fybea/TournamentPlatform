package com.platform.TournamentPlatform.exception;

import org.springframework.http.ResponseEntity;

public class NotCreatedException extends RuntimeException {
    public NotCreatedException(String message) {
        super(message);
    }
}

