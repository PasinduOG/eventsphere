package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidAuthCodeException extends ApiException {
    public InvalidAuthCodeException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
