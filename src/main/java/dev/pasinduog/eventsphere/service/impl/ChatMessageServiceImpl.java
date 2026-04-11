package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.dto.ChatMessageRequest;
import dev.pasinduog.eventsphere.dto.ChatMessageResponse;
import dev.pasinduog.eventsphere.repository.ChatMessageRepository;
import dev.pasinduog.eventsphere.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageResponse saveMessage(String eventId, ChatMessageRequest request) {
        return chatMessageRepository.saveMessage(eventId, request.senderId(), request.message());
    }
}
