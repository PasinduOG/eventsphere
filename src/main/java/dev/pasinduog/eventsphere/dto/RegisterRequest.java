package dev.pasinduog.eventsphere.dto;

public record RegisterRequest(
        String fullName,
        String email,
        String password,
        String skillsAndInterests
) {
}
