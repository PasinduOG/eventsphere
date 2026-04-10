package dev.pasinduog.eventsphere.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String id;
    private String fullName;
    private String email;
    private String passwordHash;
    private String role;
    private String skillsAndInterests;
    private LocalDateTime createdAt;
}