package dev.pasinduog.eventsphere.repository.impl;

import dev.pasinduog.eventsphere.dto.ChatMessageResponse;
import dev.pasinduog.eventsphere.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ChatMessageResponse saveMessage(String eventId, String senderId, String message) {
        String id = UUID.randomUUID().toString();
        LocalDateTime sentAt = LocalDateTime.now();
        String sql = "INSERT INTO chat_messages (id, event_id, sender_id, message, sent_at) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, id, eventId, senderId, message, sentAt);
        return new ChatMessageResponse(id, eventId, senderId, message, sentAt);
    }
}
