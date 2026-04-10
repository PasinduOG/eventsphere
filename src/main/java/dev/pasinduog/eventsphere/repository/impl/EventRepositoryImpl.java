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
    public Optional<Event> findById(String id) {
        String sql =  "SELECT id, organizer_id, title, description, start_time, end_time, max_attendees, status, created_at FROM events WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper(), id).stream().findFirst();
    }

    @Override
    public List<Event> findUpcomingEvents() {
        String sql =  "SELECT id, organizer_id, title, description, start_time, end_time, max_attendees, status, created_at" +
                " FROM events WHERE status = 'UPCOMING' AND start_time > NOW() ORDER BY start_time";
        return jdbcTemplate.query(sql, rowMapper());
    }
}
