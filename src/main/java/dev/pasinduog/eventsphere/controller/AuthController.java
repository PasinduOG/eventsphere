package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.LoginRequest;
import dev.pasinduog.eventsphere.dto.LoginResponse;
import dev.pasinduog.eventsphere.dto.OAuth2CallbackRequest;
import dev.pasinduog.eventsphere.exception.InvalidAuthCodeException;
import dev.pasinduog.eventsphere.exception.InvalidLoginException;
import dev.pasinduog.eventsphere.exception.UserNotFoundException;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.UserRepository;
import dev.pasinduog.eventsphere.service.JwtService;
import dev.pasinduog.eventsphere.service.OAuth2CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2CodeService oAuth2CodeService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("We couldn't find an account with that email."));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidLoginException("The password you entered is incorrect. Please try again.");
        }
        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }

    @PostMapping("/oauth2/callback")
    public LoginResponse oauth2Callback(@RequestBody OAuth2CallbackRequest request) {
        String email = oAuth2CodeService.validateCodeAndGetEmail(request.code());

        if (email == null) {
            throw new InvalidAuthCodeException("Invalid or expired authorization code");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}