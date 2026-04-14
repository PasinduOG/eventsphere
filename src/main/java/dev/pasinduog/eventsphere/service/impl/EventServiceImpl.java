package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.exception.EventNotFoundException;
import dev.pasinduog.eventsphere.exception.OutOfReachException;
import dev.pasinduog.eventsphere.model.Event;
import dev.pasinduog.eventsphere.repository.EventRegistrationRepository;
import dev.pasinduog.eventsphere.repository.EventRepository;
import dev.pasinduog.eventsphere.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;

    @Override
    public boolean createEvent(Event event) {
        if (event.getId() == null || event.getId().isEmpty()) {
            event.setId(UUID.randomUUID().toString());
        }
        return eventRepository.save(event);
    }

    @Override
    public boolean updateEvent(Event event, String eventId) {
        if (event.getId() == null || event.getId().isEmpty()) {
            event.setId(eventId);
        }
        if (eventRepository.findById(eventId).isEmpty())
            throw new EventNotFoundException("Update failed. Event not found");
        return eventRepository.update(event);
    }

    @Override
    public boolean cancelEvent(String eventId) {
        if (eventRepository.findById(eventId).isEmpty())
            throw new EventNotFoundException("Update failed. Event not found");
        return eventRepository.cancelEvent(eventId);
    }

    @Override
    public boolean softDelete(String eventId) {
        if (eventRepository.findById(eventId).isEmpty())
            throw new EventNotFoundException("Update failed. Event not found");
        return eventRepository.softDelete(eventId);
    }

    @Override
    public boolean delete(String eventId) {
        if (eventRepository.findById(eventId).isEmpty())
            throw new EventNotFoundException("Update failed. Event not found");
        return eventRepository.delete(eventId);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean registerUserForEvent(String eventId, String userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        int currentAttendees = eventRegistrationRepository.countRegistrationsByEventId(eventId);
        if (currentAttendees >= event.getMaxAttendees()) {
            throw new OutOfReachException("Maximum number of attendees reached");
        }
        return eventRegistrationRepository.saveRegistration(eventId, userId);
    }

    @Override
    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents();
    }

    @Override
    public List<Event> getEventsByOrganizerEmail(String email) {
        return eventRepository.findByOrganizerEmail(email);
    }
}
