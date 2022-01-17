package logic.exception;

// @author Danilo D'Amico

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String explanation) {
        super(explanation);
    }

}
