package com.pansoftware.logic.exception;

// Exception raised when a db query doesn't return data

// @author Danilo D'Amico

public class EmptyResultSetException extends Exception {

    public EmptyResultSetException(final String explanation) {
        super(explanation);
    }
}
