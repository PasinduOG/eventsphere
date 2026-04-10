package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class EventAlreadyExistsException extends ApiException {
    public EventAlreadyExistsException(String title) {
        super("An event with title '" + title + "' already exists", HttpStatus.CONFLICT);
    }
}
