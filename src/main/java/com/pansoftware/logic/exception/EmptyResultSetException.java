package com.pansoftware.logic.exception;

// @author Danilo D'Amico

public class EmptyResultSetException extends Exception {

    public EmptyResultSetException(final String explanation) {
        super(explanation);
    }
}
