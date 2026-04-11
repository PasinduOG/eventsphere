package dev.pasinduog.eventsphere.dto;

import java.time.LocalDateTime;

public record ChatMessageResponse(String id, String eventId, String senderId, String message, LocalDateTime sentAt) {
}
