package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class LimitExceedException extends ApiException {
    public LimitExceedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
