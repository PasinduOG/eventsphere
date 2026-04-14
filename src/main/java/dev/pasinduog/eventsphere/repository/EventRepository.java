package dev.pasinduog.eventsphere.repository;

import dev.pasinduog.eventsphere.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    boolean save(Event event);
    boolean update(Event event);
    boolean cancelEvent(String eventId);
    boolean softDelete(String eventId);
    boolean delete(String eventId);
    Optional<Event> findById(String id);
    List<Event> findByOrganizerEmail(String email);
    List<Event> findUpcomingEvents();
}
