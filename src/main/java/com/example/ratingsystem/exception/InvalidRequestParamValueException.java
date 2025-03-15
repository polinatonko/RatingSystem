package com.example.ratingsystem.exception;

public class InvalidRequestParamValueException extends RuntimeException {
    public InvalidRequestParamValueException(String message) {
        super(message);
    }
}