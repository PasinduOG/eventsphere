package dev.pasinduog.eventsphere.repository;

public interface EventRegistrationRepository {
    boolean saveRegistration(String eventId, String userId);
    int countRegistrationsByEventId(String eventId);
}
