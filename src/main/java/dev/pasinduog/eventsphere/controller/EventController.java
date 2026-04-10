package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.AiMatchResult;
import dev.pasinduog.eventsphere.model.Event;
import dev.pasinduog.eventsphere.service.AiMatchmakingService;
import dev.pasinduog.eventsphere.service.EventService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    boolean createEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }

    @PostMapping("/{eventId}/register")
    boolean registerEvent(@PathVariable String eventId, @RequestParam String userId){
        return eventService.registerUserForEvent(eventId, userId);
    }

    @PostMapping("/{eventId}/matchmaking")
    AiMatchResult generateNetworkingMatches(@PathVariable String eventId, @RequestParam String userId){
        return aiMatchmakingService.generateMatchesForUser(eventId, userId);
    }
}
