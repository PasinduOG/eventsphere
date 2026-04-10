package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class OutOfReachException extends ApiException {
    public OutOfReachException(String message) {
        super(message, HttpStatus.LOCKED);
    }
}
