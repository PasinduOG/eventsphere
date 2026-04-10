package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.UserRepository;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public String registerUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return user.getId();
    }
}
