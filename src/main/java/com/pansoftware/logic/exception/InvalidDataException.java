package com.pansoftware.logic.exception;

// Data Validation exception

// @author Danilo D'Amico

public class InvalidDataException extends Exception {

    public InvalidDataException(final String explanation) {
        super(explanation);
    }

}
