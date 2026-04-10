package dev.pasinduog.eventsphere.dto;

public record UserResponse(
        String id,
        String fullName,
        String role
) {}