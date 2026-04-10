package dev.pasinduog.eventsphere.dto;

public record AiMatchResult(
        String suggestedUserId,
        int matchScore,
        String matchReason
) {}