package dev.pasinduog.eventsphere.repository.impl;

import dev.pasinduog.eventsphere.exception.UserAlreadyExistsException;
import dev.pasinduog.eventsphere.repository.EventRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventRegistrationRepositoryImpl implements EventRegistrationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean saveRegistration(String eventId, String userId) {
        try {
            String sql = "INSERT INTO event_registrations (id, event_id, user_id) VALUES (?,?,?)";
            return jdbcTemplate.update(sql, UUID.randomUUID().toString(), eventId, userId) > 0;
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException(userId);
        }
    }

    @Override
    public int countRegistrationsByEventId(String eventId) {
        String sql = "SELECT count(*) FROM event_registrations WHERE event_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, eventId);
        return count != null ? count : 0;
    }
}
