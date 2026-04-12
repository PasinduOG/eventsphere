package dev.pasinduog.eventsphere.dto;

public record LoginRequest(
        String email,
        String password
) {}
