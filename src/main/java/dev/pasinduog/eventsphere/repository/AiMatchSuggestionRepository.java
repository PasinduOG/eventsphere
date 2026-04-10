package dev.pasinduog.eventsphere.repository;

public interface AiMatchSuggestionRepository {
    void saveMatchSuggestion(String eventId, String targetUserId, String suggestedUserId, int matchScore, String matchReason);
}