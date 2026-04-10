package dev.pasinduog.eventsphere.dto;

import java.util.List;

public record GeminiResponse(List<Candidate> candidates) {
    public record Candidate(Content content) {}
    public record Content(List<Part> parts) {}
    public record Part(String text) {}

    public String getExtractedText() {
        if (candidates == null || candidates.isEmpty()) {
            return "";
        }
        Candidate candidate = candidates.get(0);
        if (candidate.content() == null) {
            return "";
        }
        List<Part> parts = candidate.content().parts();
        if (parts == null || parts.isEmpty()) {
            return "";
        }
        return parts.stream()
                .map(Part::text)
                .filter(t -> t != null && !t.isBlank())
                .findFirst()
                .orElse("");
    }
}