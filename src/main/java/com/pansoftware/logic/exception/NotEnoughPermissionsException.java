package com.pansoftware.logic.exception;

// @author Danilo D'Amico

public class NotEnoughPermissionsException extends Exception {

    public NotEnoughPermissionsException(final String explanation) {
        super(explanation);
    }

}
