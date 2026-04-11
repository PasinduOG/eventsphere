package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.dto.RegisterRequest;
import dev.pasinduog.eventsphere.dto.UserResponse;

public interface UserService {
    boolean registerUser(RegisterRequest request);
    UserResponse getUserByEmail(String email);
}
