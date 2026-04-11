package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.dto.ChatMessageRequest;
import dev.pasinduog.eventsphere.dto.ChatMessageResponse;

public interface ChatMessageService {
    ChatMessageResponse saveMessage(String eventId, ChatMessageRequest request);
}
