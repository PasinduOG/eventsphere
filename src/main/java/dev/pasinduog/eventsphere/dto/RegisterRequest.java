package dev.pasinduog.eventsphere.dto;

import io.github.og4dev.annotation.AutoTrim;
import io.github.og4dev.annotation.XssCheck;

@AutoTrim
@XssCheck
public record RegisterRequest(
        String fullName,
        String email,
        String password,
        String skillsAndInterests
) {
}
