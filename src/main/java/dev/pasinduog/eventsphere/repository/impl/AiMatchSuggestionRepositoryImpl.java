package dev.pasinduog.eventsphere.repository.impl;

import dev.pasinduog.eventsphere.model.AiMatchSuggestion;
import dev.pasinduog.eventsphere.repository.AiMatchSuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<AiMatchSuggestion> findMatchesByEventAndUser(String eventId, String targetUserId) {
        String sql = "SELECT * FROM ai_match_suggestions WHERE event_id = ? AND target_user_id = ? ORDER BY match_score DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new AiMatchSuggestion(
                rs.getString("id"),
                rs.getString("event_id"),
                rs.getString("target_user_id"),
                rs.getString("suggested_user_id"),
                rs.getInt("match_score"),
                rs.getString("match_reason")
        ), eventId, targetUserId);
    }
}
