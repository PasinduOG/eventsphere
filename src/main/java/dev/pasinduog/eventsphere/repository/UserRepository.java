package dev.pasinduog.eventsphere.repository;

import dev.pasinduog.eventsphere.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
