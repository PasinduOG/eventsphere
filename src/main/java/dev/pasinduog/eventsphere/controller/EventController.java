package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.AiMatchResult;
import dev.pasinduog.eventsphere.dto.MatchSuggestionResponse;
import dev.pasinduog.eventsphere.model.Event;
import dev.pasinduog.eventsphere.service.AiMatchmakingService;
import dev.pasinduog.eventsphere.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AiMatchmakingService aiMatchmakingService;

    @GetMapping("/upcoming")
    List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @GetMapping("/{eventId}/matches")
    @PreAuthorize("isAuthenticated()")
    List<MatchSuggestionResponse> getMatchSuggestions(@PathVariable String eventId, @RequestParam String userId) {
        return aiMatchmakingService.getMatchSuggestions(eventId, userId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER') or hasAuthority('ADMIN')")
    boolean createEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }

    @PostMapping("/{eventId}/register")
    @PreAuthorize("isAuthenticated()")
    boolean registerEvent(@PathVariable String eventId, @RequestParam String userId){
        return eventService.registerUserForEvent(eventId, userId);
    }

    @PostMapping("/{eventId}/matchmaking")
    @PreAuthorize("isAuthenticated()")
    AiMatchResult generateNetworkingMatches(@PathVariable String eventId, @RequestParam String userId){
        return aiMatchmakingService.generateMatchesForUser(eventId, userId);
    }
}
