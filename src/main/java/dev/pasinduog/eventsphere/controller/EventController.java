package dev.pasinduog.eventsphere.controller;

import dev.pasinduog.eventsphere.model.Event;
import dev.pasinduog.eventsphere.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    boolean createEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }

    @PostMapping("/{eventId}/register")
    boolean registerEvent(@PathVariable String eventId, @RequestParam String userId){
        return eventService.registerUserForEvent(eventId, userId);
    }
}
