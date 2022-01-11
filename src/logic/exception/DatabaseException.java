package logic.exception;

// @author Danilo D'Amico

public class DatabaseException extends Exception {

	private static final long serialVersionUID = 8308910018191726934L;
	
	public DatabaseException(String explanation) {
		super(explanation);
	}

}
