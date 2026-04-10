package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String id) {
        super("A user with ID '" + id + "' already exists", HttpStatus.CONFLICT);
    }
}
