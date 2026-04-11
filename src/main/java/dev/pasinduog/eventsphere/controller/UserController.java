package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.RegisterRequest;
import dev.pasinduog.eventsphere.dto.UserResponse;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    boolean registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping("/by-email")
    UserResponse getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}
