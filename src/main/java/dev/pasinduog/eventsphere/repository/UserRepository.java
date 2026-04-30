package dev.pasinduog.eventsphere.repository;

import dev.pasinduog.eventsphere.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean save(User user);
    boolean update(User user);
    Optional<User> findById(String id);
    List<User> findByIds(List<String> ids);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findRandomAttendeesForMatchmaking(String eventId, String excludeUserId, int limit);
    void incrementAiMatchCount(String userId);
}
