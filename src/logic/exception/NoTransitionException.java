package logic.exception;

// @author Danilo D'Amico

public class NoTransitionException extends Exception {

    public NoTransitionException() {
        super("No transition occurs performing this action in the current state");
    }

}
