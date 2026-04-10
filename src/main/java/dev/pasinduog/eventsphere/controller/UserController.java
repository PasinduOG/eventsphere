package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    boolean registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}
