package dev.pasinduog.eventsphere.dto;

public record UserResponse(
        String id,
        String fullName,
        String email,
        String role,
        String skillsAndInterests
) {}