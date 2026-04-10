package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.exception.UserAlreadyExistsException;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        try {
            String userId = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", userId));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Email address is already in use"));
        }
    }
}
