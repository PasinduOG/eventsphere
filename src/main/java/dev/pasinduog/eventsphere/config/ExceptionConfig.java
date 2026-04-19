package dev.pasinduog.eventsphere.config;

import io.github.og4dev.exception.ApiExceptionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import java.sql.SQLException;

@Configuration
public class ExceptionConfig {

    @Bean
    public ApiExceptionRegistry apiExceptionRegistry() {
        return new ApiExceptionRegistry()
                .register(SQLException.class, HttpStatus.INTERNAL_SERVER_ERROR, "A Database error occurred")
                .register(AuthenticationException.class, HttpStatus.UNAUTHORIZED, "Authentication required");
    }
}
