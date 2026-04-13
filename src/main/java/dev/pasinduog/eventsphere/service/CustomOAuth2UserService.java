package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final Logger logger;
    private final PasswordEncoder passwordEncoder;

    @Value("#{'${security.admin-emails:}'.empty ? {} : '${security.admin-emails:}'.split(',')}")
    private final List<String> adminEmails;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        if (name == null) name = oAuth2User.getAttribute("login");

        if (email == null) {
            email = fetchEmailFromGitHub(userRequest.getAccessToken().getTokenValue());
        }

        if (email == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("email_not_found", "Email not found from GitHub provider", null));
        }

        oAuth2User = enhanceOAuth2UserWithEmail(oAuth2User, email);
        registerNewUserIfNeeded(email, name);

        return oAuth2User;
    }

    private String fetchEmailFromGitHub(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://api.github.com/user/emails",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        List<Map<String, Object>> emails = response.getBody();
        if (emails != null) {
            for (Map<String, Object> emailObj : emails) {
                if (Boolean.TRUE.equals(emailObj.get("primary")) && Boolean.TRUE.equals(emailObj.get("verified"))) {
                    return (String) emailObj.get("email");
                }
            }
        }
        return null;
    }

    private OAuth2User enhanceOAuth2UserWithEmail(OAuth2User oAuth2User, String email) {
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        if (!attributes.containsKey("email") || attributes.get("email") == null) {
            attributes.put("email", email);
        }
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), attributes, "email");
    }

    private void registerNewUserIfNeeded(String email, String name) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setFullName(name);
            user.setEmail(email);
            user.setPasswordHash(passwordEncoder.encode(UUID.randomUUID().toString()));
            if (adminEmails.contains(email)) {
                user.setRole("ADMIN");
            } else {
                user.setRole("ATTENDEE");
            }
            user.setSkillsAndInterests("GitHub User");
            user.setCreatedAt(LocalDateTime.now());

            userRepository.save(user);
            logger.info("New user registered via GitHub with role: " + user.getRole());
        } else {
            logger.warning("User already exists");
        }
    }
}
