package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.dto.RegisterRequest;
import dev.pasinduog.eventsphere.dto.UserResponse;
import dev.pasinduog.eventsphere.exception.UserAlreadyExistsException;
import dev.pasinduog.eventsphere.exception.UserNotFoundException;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.UserRepository;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole("ATTENDEE");
        user.setSkillsAndInterests(request.skillsAndInterests());
        return userRepository.save(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getSkillsAndInterests()
        );
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.update(user);
    }

    @Override
    public User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
