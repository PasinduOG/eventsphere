package dev.pasinduog.eventsphere.repository;

import dev.pasinduog.eventsphere.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}
