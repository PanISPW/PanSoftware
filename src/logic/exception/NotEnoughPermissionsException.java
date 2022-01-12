package logic.exception;

// @author Danilo D'Amico

public class NotEnoughPermissionsException extends Exception {

    private static final long serialVersionUID = -4925317221938314871L;

    public NotEnoughPermissionsException() {
        super("Your type of user cannot perform this action");
    }

}
