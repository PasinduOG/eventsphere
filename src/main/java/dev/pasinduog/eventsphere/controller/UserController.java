package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.RegisterRequest;
import dev.pasinduog.eventsphere.dto.UserResponse;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/by-email")
    UserResponse getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    boolean registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @PutMapping("/{userId}/profile")
    boolean updateProfile(@PathVariable String userId, @RequestBody Map<String, String> updates){
        User user = userService.getUserById(userId);
        if (updates.containsKey("skillsAndInterests")) user.setSkillsAndInterests(updates.get("skillsAndInterests"));
        if (updates.containsKey("fullName")) user.setSkillsAndInterests(updates.get("fullName"));
        return userService.updateUser(user);
    }
}
