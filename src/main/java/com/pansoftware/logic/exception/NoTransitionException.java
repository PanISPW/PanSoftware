package com.pansoftware.logic.exception;

// exception to manage State Machine events

// @author Danilo D'Amico

public class NoTransitionException extends Exception {

    public NoTransitionException(final String explanation) {
        super(explanation);
    }

}
