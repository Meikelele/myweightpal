package com.example.myweightpal.bootstrap;

import com.example.myweightpal.model.Role;
import com.example.myweightpal.model.User;
import com.example.myweightpal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DataSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        log.info("Starting data seeding...");

        final String email = "king@demo.pl";
        if (!users.existsByEmail(email)) {
            var user = User.builder()
                    .email(email)
                    .username("King Demo")
                    .password(encoder.encode("test123"))
                    .role(Role.KING)
                    .nickname("King Demo")
                    .language("PL")
                    .theme("light")
                    .avatarId("king_avatar_1")
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .currentStreak(0)
                    .longestStreak(0)
                    .totalEntries(0)
                    .build();

            users.save(user);
            log.info("✅ Seeded user: {} / test123 (role=KING)", email);
            System.out.println("✅ Seeded user: " + email + " / test123 (role=KING)");

        } else {
            log.info("⚠️ User already exists: {} - skipping seed", email);
            System.out.println("⚠️ User already exists: " + email + " - skipping seed");
        }

        log.info("Data seeding completed.");
    }
}