package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.model.Event;

import java.util.List;

public interface EventService {
    boolean createEvent(Event event);
    boolean registerUserForEvent(String eventId, String userId);
    List<Event> getUpcomingEvents();
}
