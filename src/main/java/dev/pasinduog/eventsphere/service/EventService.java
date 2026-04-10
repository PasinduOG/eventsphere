package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.model.Event;

public interface EventService {
    boolean createEvent(Event event);
    boolean registerUserForEvent(String eventId, String userId);
}
