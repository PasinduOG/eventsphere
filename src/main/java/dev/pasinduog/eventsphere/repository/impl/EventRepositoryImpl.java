package dev.pasinduog.eventsphere.repository.impl;

import dev.pasinduog.eventsphere.exception.EventAlreadyExistsException;
import dev.pasinduog.eventsphere.model.Event;
import dev.pasinduog.eventsphere.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Event> rowMapper() {
        return (rs, rowNum) -> new Event(
                rs.getString("id"),
                rs.getString("organizer_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getTimestamp("start_time").toLocalDateTime(),
                rs.getTimestamp("end_time").toLocalDateTime(),
                rs.getInt("max_attendees"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    @Override
    public boolean save(Event event) {
        try {
            if (event.getOrganizerId() == null) return false;
            String sql = "INSERT INTO events (id, organizer_id, title, description, start_time, end_time, max_attendees, status) VALUES (?,?,?,?,?,?,?,?)";
            return jdbcTemplate.update(sql,
                    event.getId(),
                    event.getOrganizerId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getStartTime(),
                    event.getEndTime(),
                    event.getMaxAttendees(),
                    event.getStatus()) > 0;
        } catch (DuplicateKeyException e) {
            throw new EventAlreadyExistsException(event.getTitle());
        }
    }

    @Override
    public boolean update(Event event) {
        try {
            if (event.getOrganizerId() == null) return false;
            String sql = "UPDATE events SET title = ?, description = ?, start_time = ?, end_time = ?, " +
                    "max_attendees = ?, status = ? WHERE id = ?";
            return jdbcTemplate.update(sql,
                    event.getTitle(),
                    event.getDescription(),
                    event.getStartTime(),
                    event.getEndTime(),
                    event.getMaxAttendees(),
                    event.getStatus(),
                    event.getId()) > 0;
        } catch (DuplicateKeyException e) {
            throw new EventAlreadyExistsException(event.getTitle());
        }
    }

    @Override
    public boolean cancelEvent(String eventId) {
        String sql = "UPDATE events SET status = 'CANCELLED' WHERE id = ?";
        return jdbcTemplate.update(sql, eventId) > 0;
    }

    @Override
    public boolean softDelete(String eventId) {
        String sql = "UPDATE events SET status = 'UNAVAILABLE' WHERE id = ?";
        return jdbcTemplate.update(sql, eventId) > 0;
    }

    @Override
    public boolean delete(String eventId) {
        String sql = "DELETE FROM events WHERE id = ?";
        return jdbcTemplate.update(sql, eventId) > 0;
    }

    @Override
    public Optional<Event> findById(String id) {
        String sql = "SELECT id, organizer_id, title, description, start_time, end_time, max_attendees, status, " +
                "created_at FROM events WHERE id = ? AND status NOT IN ('CANCELLED', 'UNAVAILABLE')";
        return jdbcTemplate.query(sql, rowMapper(), id).stream().findFirst();
    }

    @Override
    public List<Event> findByOrganizerEmail(String email) {
        String sql = "SELECT e.* FROM events e " +
                "INNER JOIN users u ON e.organizer_id = u.id " +
                "WHERE u.email = ? AND e.status NOT IN ('CANCELLED', 'UNAVAILABLE')";
        return jdbcTemplate.query(sql, rowMapper(), email);
    }

    @Override
    public List<Event> findUpcomingEvents() {
        String sql =  "SELECT id, organizer_id, title, description, start_time, end_time, max_attendees, status, created_at" +
                " FROM events WHERE status = 'UPCOMING' AND start_time > NOW() ORDER BY start_time";
        return jdbcTemplate.query(sql, rowMapper());
    }
}
