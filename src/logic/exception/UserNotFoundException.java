package logic.exception;

// @author Danilo D'Amico

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -1482962286272110475L;
	
	public UserNotFoundException(String explanation) {
		super(explanation);
	}

}
