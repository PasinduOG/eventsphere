package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.model.Event;

import java.util.List;

public interface EventService {
    boolean createEvent(Event event);
    boolean updateEvent(Event event, String eventId, String email);
    boolean cancelEvent(String eventId);
    boolean softDelete(String eventId);
    boolean delete(String eventId);
    boolean registerUserForEvent(String eventId, String userId);
    List<Event> getUpcomingEvents();
    List<Event> getEventsByOrganizerEmail(String email);
}
