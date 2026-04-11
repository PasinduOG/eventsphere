package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.ChatMessageRequest;
import dev.pasinduog.eventsphere.dto.ChatMessageResponse;
import dev.pasinduog.eventsphere.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{eventId}")
    @SendTo("/topic/events/{eventId}")
    ChatMessageResponse sendMessage(@DestinationVariable String eventId, ChatMessageRequest request) {
        return chatMessageService.saveMessage(eventId, request);
    }
}
