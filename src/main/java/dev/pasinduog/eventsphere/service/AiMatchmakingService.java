package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.dto.AiMatchResult;
import dev.pasinduog.eventsphere.dto.MatchSuggestionResponse;

import java.util.List;

public interface AiMatchmakingService {
    AiMatchResult generateMatchesForUser(String eventId, String targetUserId);
    List<MatchSuggestionResponse> getMatchSuggestions(String eventId, String targetUserId);
}
