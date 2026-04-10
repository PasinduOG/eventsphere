package dev.pasinduog.eventsphere.repository;

import dev.pasinduog.eventsphere.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    boolean save(Event event);
    Optional<Event> findById(String id);
    List<Event> findUpcomingEvents();
}
