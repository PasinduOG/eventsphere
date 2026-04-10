package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class EventNotFoundException extends ApiException {
    public EventNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
