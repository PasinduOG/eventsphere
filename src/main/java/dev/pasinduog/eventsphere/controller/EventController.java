package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.dto.AiMatchResult;
import dev.pasinduog.eventsphere.dto.MatchSuggestionResponse;
import dev.pasinduog.eventsphere.model.Event;
import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.service.AiMatchmakingService;
import dev.pasinduog.eventsphere.service.EventService;
import dev.pasinduog.eventsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AiMatchmakingService aiMatchmakingService;
    private final UserService userService;

    @GetMapping("/upcoming")
    List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @GetMapping("/{eventId}")
    Event getEventById(@PathVariable String eventId) {
        return eventService.getEventById(eventId);
    }

    @GetMapping("/{eventId}/matches")
    @PreAuthorize("isAuthenticated()")
    List<MatchSuggestionResponse> getMatchSuggestions(@PathVariable String eventId, Principal principal) {
        User currentUser = userService.getUserEntityByEmail(principal.getName());
        return aiMatchmakingService.getMatchSuggestions(eventId, currentUser.getId());
    }


    @PostMapping("/{eventId}/register")
    @PreAuthorize("isAuthenticated()")
    boolean registerForEvent(@PathVariable String eventId, Principal principal) {
        User currentUser = userService.getUserEntityByEmail(principal.getName());
        return eventService.registerUserForEvent(eventId, currentUser.getId());
    }

    @PostMapping("/{eventId}/matchmaking")
    @PreAuthorize("isAuthenticated()")
    AiMatchResult generateNetworkingMatches(@PathVariable String eventId, Principal principal) {
        User currentUser = userService.getUserEntityByEmail(principal.getName());
        return aiMatchmakingService.generateMatchesForUser(eventId, currentUser.getId());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER') or hasAuthority('ADMIN')")
    boolean createEvent(@RequestBody Event event, Principal principal) {
        User currentUser = userService.getUserEntityByEmail(principal.getName());
        event.setOrganizerId(currentUser.getId());
        return eventService.createEvent(event);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ORGANIZER') or hasAuthority('ADMIN')")
    boolean updateEvent(@PathVariable String eventId, @RequestBody Event event, Principal principal) {
        return eventService.updateEvent(event, eventId, principal.getName());
    }

    @PutMapping("/{eventId}/cancel")
    @PreAuthorize("hasAuthority('ADMIN')")
    boolean cancelEvent(@PathVariable String eventId) {
        return eventService.cancelEvent(eventId);
    }

    @DeleteMapping("/{eventId}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    boolean softDeleteEvent(@PathVariable String eventId) {
        return eventService.softDelete(eventId);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    boolean deleteEvent(@PathVariable String eventId) {
        return eventService.delete(eventId);
    }

    @GetMapping("/by-organizer-email")
    @PreAuthorize("hasAuthority('ADMIN')")
    List<Event> findEventsByOrganizerEmail(@RequestParam String email) {
        return eventService.getEventsByOrganizerEmail(email);
    }

    @GetMapping("/my-events")
    @PreAuthorize("hasAuthority('ORGANIZER') or hasAuthority('ADMIN')")
    List<Event> getMyEvents(Principal principal) {
        return eventService.getEventsByOrganizerEmail(principal.getName());
    }
}
