package logic.exception;

// @author Danilo D'Amico

public class InvalidDataException extends Exception {

    private static final long serialVersionUID = -365345356225425647L;

    public InvalidDataException(String explanation) {
        super(explanation);
    }

}
