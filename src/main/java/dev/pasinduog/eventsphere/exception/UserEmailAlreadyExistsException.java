package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserEmailAlreadyExistsException extends ApiException {
    public UserEmailAlreadyExistsException(String email) {
        super("A user with email '" + email + "' already exists", HttpStatus.CONFLICT);
    }
}
