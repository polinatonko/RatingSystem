package com.example.ratingsystem.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(UUID id) {
        super("Entity with id=" + id + " not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}