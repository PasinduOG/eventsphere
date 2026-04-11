package dev.pasinduog.eventsphere.service;

import dev.pasinduog.eventsphere.model.User;
import dev.pasinduog.eventsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final Logger logger;
    private final PasswordEncoder passwordEncoder;

    private final List<String> adminEmails = List.of(
            "pasinduogdev@gmail.com",
            "pasinduogdev2@gmail.com"
    );

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        if (name == null) name = oAuth2User.getAttribute("login");
        if (email == null) throw new OAuth2AuthenticationException("Email not found from GitHub provider");
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
        return oAuth2User;
    }
}
