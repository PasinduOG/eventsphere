package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.dto.AiMatchResult;
import dev.pasinduog.eventsphere.dto.MatchSuggestionResponse;
import dev.pasinduog.eventsphere.model.User;

import java.util.List;

public interface AiMatchmakingService {
    AiMatchResult generateMatchesForUser(String eventId, String targetUserId);
    String buildPrompt(User targetUser, List<User> otherUsers);
    List<MatchSuggestionResponse> getMatchSuggestions(String eventId, String targetUserId);
}
