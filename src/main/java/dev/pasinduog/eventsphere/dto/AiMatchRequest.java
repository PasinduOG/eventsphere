package dev.pasinduog.eventsphere.dto;

import java.util.List;

public record AiMatchRequest(UserProfile targetUser, List<UserProfile> otherUsers) {
}
