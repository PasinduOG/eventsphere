package dev.pasinduog.eventsphere.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AiMatchSuggestion {
    private String id;
    private String eventId;
    private String targetUserId;
    private String suggestedUserId;
    private Integer matchScore;
    private String matchReason;
}
