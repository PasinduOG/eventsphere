package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.RegisterRequest;
import dev.pasinduog.eventsphere.dto.UserResponse;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/by-email")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ORGANIZER')")
    UserResponse getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ORGANIZER')")
    UserResponse getUserById(@RequestParam String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    UserResponse getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userService.getUserByEmail(email);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    boolean registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @PutMapping("/me/profile")
    @PreAuthorize("isAuthenticated()")
    boolean updateProfile(Principal principal, @RequestBody Map<String, String> updates){
        User user = userService.getUserEntityByEmail(principal.getName());
        if (updates.containsKey("skillsAndInterests")) user.setSkillsAndInterests(updates.get("skillsAndInterests"));
        if (updates.containsKey("fullName")) user.setFullName(updates.get("fullName"));
        return userService.updateUser(user);
    }
}
