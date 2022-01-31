package com.pansoftware.logic.exception;

// Exception for the Login Use Case

// @author Danilo D'Amico

public class UserNotFoundException extends Exception {

    public UserNotFoundException(final String explanation) {
        super(explanation);
    }

}
