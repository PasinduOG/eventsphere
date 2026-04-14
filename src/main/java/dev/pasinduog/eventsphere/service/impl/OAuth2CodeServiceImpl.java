package dev.pasinduog.eventsphere.service.impl;

import dev.pasinduog.eventsphere.service.OAuth2CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2CodeServiceImpl implements OAuth2CodeService {

    private final StringRedisTemplate redisTemplate;
    private static final Duration EXPIRATION_TIME = Duration.ofMinutes(5);

    @Override
    public String generateCode(String email) {
        String code = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("oauth2_code:" + code, email, EXPIRATION_TIME);
        return code;
    }

    @Override
    public String validateCodeAndGetEmail(String code) {
        String key = "oauth2_code:" + code;
        String email = redisTemplate.opsForValue().get(key);
        if (email == null) {
            redisTemplate.delete(key);
        }
        return email;
    }
}
