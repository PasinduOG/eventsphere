package dev.pasinduog.eventsphere.repository;

import dev.pasinduog.eventsphere.dto.ChatMessageResponse;

public interface ChatMessageRepository {
    ChatMessageResponse saveMessage(String eventId, String senderId, String message);
}
