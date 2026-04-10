package dev.pasinduog.eventsphere.repository;

import java.util.List;

public interface EventRegistrationRepository {
    boolean saveRegistration(String eventId, String userId);
    int countRegistrationsByEventId(String eventId);
    List<String> findUserIdsByEventId(String eventId);
}
