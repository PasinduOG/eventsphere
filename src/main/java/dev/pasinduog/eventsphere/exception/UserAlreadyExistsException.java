package dev.pasinduog.eventsphere.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email, Throwable cause) {
        super("A user with email '" + email + "' already exists", cause);
    }
}
