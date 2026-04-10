package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.exception.EventNotFoundException;
import dev.pasinduog.eventsphere.exception.OutOfReachException;
import dev.pasinduog.eventsphere.model.Event;
import dev.pasinduog.eventsphere.repository.EventRegistrationRepository;
import dev.pasinduog.eventsphere.repository.EventRepository;
import dev.pasinduog.eventsphere.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
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
}
