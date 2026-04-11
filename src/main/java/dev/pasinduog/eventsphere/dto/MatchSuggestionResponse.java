package dev.pasinduog.eventsphere.dto;

public record MatchSuggestionResponse(
        String matchId,
        int matchScore,
        String matchReason,
        UserResponse suggestedUser
) {}