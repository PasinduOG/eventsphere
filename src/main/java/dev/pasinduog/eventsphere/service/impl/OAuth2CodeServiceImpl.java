package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.service.OAuth2CodeService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OAuth2CodeServiceImpl implements OAuth2CodeService {
    private final Map<String, String> authCodes = new ConcurrentHashMap<>();

    @Override
    public String generateCode(String email) {
        String code = UUID.randomUUID().toString();
        authCodes.put(code, email);
        return code;
    }

    @Override
    public String validateCodeAndGetEmail(String code) {
        return authCodes.remove(code);
    }
}
