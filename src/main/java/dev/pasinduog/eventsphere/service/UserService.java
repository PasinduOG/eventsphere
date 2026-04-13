package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.dto.RegisterRequest;
import dev.pasinduog.eventsphere.dto.UserResponse;
import dev.pasinduog.eventsphere.model.User;

public interface UserService {
    boolean registerUser(RegisterRequest request);
    UserResponse getUserByEmail(String email);
    User getUserById(String userId);
    boolean updateUser(User user);
    User getUserEntityByEmail(String email);
}
