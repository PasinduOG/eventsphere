package dev.pasinduog.eventsphere.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pasinduog.eventsphere.dto.AiMatchResult;
import dev.pasinduog.eventsphere.dto.GeminiRequest;
import dev.pasinduog.eventsphere.dto.GeminiResponse;
import dev.pasinduog.eventsphere.exception.AiMatchmakingException;
import dev.pasinduog.eventsphere.exception.UserNotFoundException;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.AiMatchSuggestionRepository;
import dev.pasinduog.eventsphere.repository.UserRepository;
import dev.pasinduog.eventsphere.service.AiMatchmakingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiMatchmakingServiceImpl implements AiMatchmakingService {
    private final RestClient restClient;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final AiMatchSuggestionRepository aiMatchSuggestionRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Override
    public AiMatchResult generateMatchesForUser(String eventId, String targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + targetUserId));

        List<User> otherUsers = userRepository.findAll().stream()
                .filter(u -> !u.getId().equals(targetUserId))
                .toList();

        String prompt = buildPrompt(targetUser, otherUsers);

        GeminiResponse response = restClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .body(GeminiRequest.of(prompt))
                .retrieve()
                .body(GeminiResponse.class);

        assert response != null;
        String aiResultString = response.getExtractedText();

        try {
            String cleanJson = aiResultString.replace("```json", "").replace("```", "").trim();
            AiMatchResult matchResult = objectMapper.readValue(cleanJson, AiMatchResult.class);

            aiMatchSuggestionRepository.saveMatchSuggestion(
                    eventId,
                    targetUserId,
                    matchResult.suggestedUserId(),
                    matchResult.matchScore(),
                    matchResult.matchReason()
            );

            return matchResult;

        } catch (Exception e) {
            throw new AiMatchmakingException("Failed to process AI Matchmaking: " + e.getMessage());
        }
    }

    @Override
    public String buildPrompt(User targetUser, List<User> otherUsers) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an expert AI Networking Matchmaker for a Tech Event. ");
        prompt.append("Find the best person to network with for the following Target User based on their skills and interests.\n\n");

        prompt.append("Target User:\n");
        prompt.append("- ID: ").append(targetUser.getId()).append("\n");
        prompt.append("- Name: ").append(targetUser.getFullName()).append("\n");
        prompt.append("- Skills: ").append(targetUser.getSkillsAndInterests()).append("\n\n");

        prompt.append("Available Attendees to match with:\n");
        for (User u : otherUsers) {
            prompt.append("- ID: ").append(u.getId()).append(" | Name: ").append(u.getFullName())
                    .append(" | Skills: ").append(u.getSkillsAndInterests()).append("\n");
        }

        prompt.append("\nAnalyze the Target User's skills against the Available Attendees. ");
        prompt.append("Return the single BEST match as a JSON object strictly in this format without any markdown wrappers:\n");
        prompt.append("{\n");
        prompt.append("  \"suggestedUserId\": \"user_id_here\",\n");
        prompt.append("  \"matchScore\": 95,\n");
        prompt.append("  \"matchReason\": \"Short reason why they match\"\n");
        prompt.append("}");

        return prompt.toString();
    }
}
