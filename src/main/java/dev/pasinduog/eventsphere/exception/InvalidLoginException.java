package dev.pasinduog.eventsphere.exception;
import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidLoginException extends ApiException {

    public InvalidLoginException(String customMessage) {
        super(customMessage, HttpStatus.UNAUTHORIZED);
    }
}