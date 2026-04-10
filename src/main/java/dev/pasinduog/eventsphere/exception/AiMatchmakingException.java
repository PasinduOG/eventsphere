package dev.pasinduog.eventsphere.exception;

import io.github.og4dev.exception.ApiException;
import org.springframework.http.HttpStatus;

public class AiMatchmakingException extends ApiException {
    public AiMatchmakingException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
