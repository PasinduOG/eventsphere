package dev.pasinduog.eventsphere.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pasinduog.eventsphere.dto.AiMatchResult;
import dev.pasinduog.eventsphere.dto.GeminiRequest;
import dev.pasinduog.eventsphere.dto.GeminiResponse;
import dev.pasinduog.eventsphere.dto.UserResponse;
import dev.pasinduog.eventsphere.dto.MatchSuggestionResponse;
import dev.pasinduog.eventsphere.exception.AiMatchmakingException;
import dev.pasinduog.eventsphere.exception.UserNotFoundException;
import dev.pasinduog.eventsphere.model.AiMatchSuggestion;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.AiMatchSuggestionRepository;
import dev.pasinduog.eventsphere.repository.EventRegistrationRepository;
import dev.pasinduog.eventsphere.repository.UserRepository;
import dev.pasinduog.eventsphere.service.AiMatchmakingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AiMatchmakingServiceImpl implements AiMatchmakingService {
    private final RestClient restClient;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final AiMatchSuggestionRepository aiMatchSuggestionRepository;
    private final EventRegistrationRepository eventRegistrationRepository;

    @Value("${Gemini.api.key}")
    private String apiKey;

    @Value("${Gemini.api.url}")
    private String apiUrl;

    @Override
    public AiMatchResult generateMatchesForUser(String eventId, String targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<String> registeredUserIds = eventRegistrationRepository.findUserIdsByEventId(eventId);

        if (!registeredUserIds.contains(targetUserId)) {
            throw new UserNotFoundException("Target user is not registered for this event!");
        }

        List<User> attendeesForMatchmaking = userRepository.findAll().stream()
                .filter(u -> registeredUserIds.contains(u.getId()))
                .filter(u -> !u.getId().equals(targetUserId))
                .toList();

        String prompt = buildPrompt(targetUser, attendeesForMatchmaking);

        GeminiResponse response = restClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .body(GeminiRequest.of(prompt))
                .retrieve()
                .body(GeminiResponse.class);

        Objects.requireNonNull(response, "Gemini API response cannot be null");
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
        prompt.append("- Skills: ").append(targetUser.getSkillsAndInterests()).append("\n\n");

        prompt.append("Available Attendees to match with:\n");
        for (User u : otherUsers) {
            prompt.append("- ID: ").append(u.getId())
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

    @Override
    public List<MatchSuggestionResponse> getMatchSuggestions(String eventId, String targetUserId) {
        List<AiMatchSuggestion> rowMatches = aiMatchSuggestionRepository.findMatchesByEventAndUser(eventId, targetUserId);
        if (rowMatches.isEmpty()) {
            return List.of();
        }

        List<String> suggestedUserIds = rowMatches.stream()
                .map(AiMatchSuggestion::getSuggestedUserId)
                .toList();

        List<User> suggestedUsers = userRepository.findByIds(suggestedUserIds);
        java.util.Map<String, User> userMap = suggestedUsers.stream()
                .collect(java.util.stream.Collectors.toMap(User::getId, u -> u));

        return rowMatches.stream().map(match -> {
            User suggestedUser = userMap.get(match.getSuggestedUserId());

            if (suggestedUser == null) {
                throw new UserNotFoundException(
                        "Suggested user not found: suggestedUserId=" + match.getSuggestedUserId()
                                + ", matchId=" + match.getId()
                                + ", eventId=" + eventId
                                + ", targetUserId=" + targetUserId);
            }

            UserResponse userResponse = new UserResponse(
                    suggestedUser.getId(),
                    suggestedUser.getFullName(),
                    suggestedUser.getEmail(),
                    suggestedUser.getRole(),
                    suggestedUser.getSkillsAndInterests()
            );

            return new MatchSuggestionResponse(
                    match.getId(),
                    match.getMatchScore(),
                    match.getMatchReason(),
                    userResponse
            );
        }).toList();
    }
}
