package com.pansoftware.logic.exception;

// eccezione gestita

// @author Danilo D'Amico

public class UserNotFoundException extends Exception {

    public UserNotFoundException(final String explanation) {
        super(explanation);
    }

}
