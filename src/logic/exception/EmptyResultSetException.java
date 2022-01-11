package logic.exception;

// @author Danilo D'Amico

public class EmptyResultSetException extends Exception {

	private static final long serialVersionUID = -4383926431656283680L;

	public EmptyResultSetException(String explanation) {
		super(explanation);
	}
}
