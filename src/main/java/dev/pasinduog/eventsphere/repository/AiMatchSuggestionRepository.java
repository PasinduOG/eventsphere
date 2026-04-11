package dev.pasinduog.eventsphere.repository;

import dev.pasinduog.eventsphere.model.AiMatchSuggestion;

import java.util.List;

public interface AiMatchSuggestionRepository {
    void saveMatchSuggestion(String eventId, String targetUserId, String suggestedUserId, int matchScore, String matchReason);
    List<AiMatchSuggestion> findMatchesByEventAndUser(String eventId, String targetUserId);
}