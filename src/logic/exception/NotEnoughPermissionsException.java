package logic.exception;

// @author Danilo D'Amico

public class NotEnoughPermissionsException extends Exception {

    public NotEnoughPermissionsException() {
        super("Your type of user cannot perform this action");
    }

}
