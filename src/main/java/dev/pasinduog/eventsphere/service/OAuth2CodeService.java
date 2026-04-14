package dev.pasinduog.eventsphere.service;

public interface OAuth2CodeService {
    String generateCode(String email);
    String validateCodeAndGetEmail(String code);
}
