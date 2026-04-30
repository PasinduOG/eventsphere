package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.dto.*;
import dev.pasinduog.eventsphere.exception.AiMatchmakingException;
import dev.pasinduog.eventsphere.exception.LimitExceedException;
import dev.pasinduog.eventsphere.exception.UserNotFoundException;
import dev.pasinduog.eventsphere.model.AiMatchSuggestion;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.AiMatchSuggestionRepository;
import dev.pasinduog.eventsphere.repository.UserRepository;
import dev.pasinduog.eventsphere.service.AiMatchmakingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiMatchmakingServiceImpl implements AiMatchmakingService {
    private final RestClient restClient;
    private final UserRepository userRepository;
    private final AiMatchSuggestionRepository aiMatchSuggestionRepository;

    @Value("${ollama.api.url}")
    private String apiUrl;

    @Override
    public AiMatchResult generateMatchesForUser(String eventId, String targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!targetUser.isPremium() && targetUser.getAiMatchCount() >= 3) {
            throw new LimitExceedException("Free limit reached! You have used your 3 free AI matches. Please upgrade to Premium.");
        }

        List<User> attendeesForMatchmaking = userRepository.findRandomAttendeesForMatchmaking(eventId, targetUserId, 50);

        if (attendeesForMatchmaking.isEmpty()) {
            throw new AiMatchmakingException("No other attendees found in this event to match with!");
        }

        // 🔥 1. පිරිසිදුවට DTOs හදාගන්නවා (String කරන, Map කරන කෑලි අයින් කළා)
        UserProfile target = new UserProfile(targetUser.getId(), targetUser.getSkillsAndInterests());
        List<UserProfile> others = attendeesForMatchmaking.stream()
                .map(user -> new UserProfile(user.getId(), user.getSkillsAndInterests())).toList();

        AiMatchRequest requestPayload = new AiMatchRequest(target, others);

        try {
            // 🔥 ULTIMATE FIX: RestClient අයින් කරලා පරණ, ලෙඩ දෙන්නේ නැති RestTemplate එක පාවිච්චි කරනවා!
            RestTemplate simpleRestTemplate = new org.springframework.web.client.RestTemplate();

            // පේළි 5-6ක් තිබ්බ එක තනි පේළියෙන් යවනවා. කිසිම අමුතු Headers යන්නේ නෑ.
            AiMatchResult matchResult = simpleRestTemplate.postForObject(
                    apiUrl,
                    requestPayload,
                    AiMatchResult.class
            );

            if (matchResult == null || matchResult.suggestedUserId() == null) {
                throw new AiMatchmakingException("Invalid response from Local AI.");
            }

            // ... මෙතනින් පල්ලෙහාට Validation ටිකයි DB Save කරන ටිකයි එහෙම්මමයි ...
            boolean isValidId = attendeesForMatchmaking.stream()
                    .anyMatch(u -> u.getId().equals(matchResult.suggestedUserId()));

            if (!isValidId) {
                throw new AiMatchmakingException("AI generated an invalid User ID. Please try again.");
            }

            aiMatchSuggestionRepository.saveMatchSuggestion(
                    eventId, targetUserId, matchResult.suggestedUserId(),
                    matchResult.matchScore(), matchResult.matchReason()
            );

            userRepository.incrementAiMatchCount(targetUserId);

            return matchResult;

        } catch (Exception e) {
            throw new AiMatchmakingException("Failed to process AI Matchmaking: " + e.getMessage());
        }
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
