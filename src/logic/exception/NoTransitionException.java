package logic.exception;

// @author Danilo D'Amico

public class NoTransitionException extends Exception {

    private static final long serialVersionUID = 8625462204047370198L;

    public NoTransitionException() {
        super("No transition occurs performing this action in the current state");
    }

}
