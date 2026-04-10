package dev.pasinduog.eventsphere.repository.impl;

import dev.pasinduog.eventsphere.repository.AiMatchSuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AiMatchSuggestionRepositoryImpl implements AiMatchSuggestionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveMatchSuggestion(String eventId, String targetUserId, String suggestedUserId, int matchScore, String matchReason) {
        String sql = "INSERT INTO ai_match_suggestions (id, event_id, target_user_id, suggested_user_id, match_score, match_reason) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                UUID.randomUUID().toString(),
                eventId,
                targetUserId,
                suggestedUserId,
                matchScore,
                matchReason
        );
    }
}
