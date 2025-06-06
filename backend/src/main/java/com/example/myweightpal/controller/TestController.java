package com.example.myweightpal.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.myweightpal.model.Role;
import com.example.myweightpal.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    // Dodaj tę metodę do swojego TestController.java

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> testUserModel() {
        Map<String, Object> response = new HashMap<>();


            User user1 = new User("jan123", "jan@example.com", "password123", Role.KING);

            // Test 3: Testowanie metod biznesowych
            boolean kingVsPeasant = Role.KING.hasHigherRankThan(Role.PEASANT); // true
            boolean peasantVsKing = Role.PEASANT.hasHigherRankThan(Role.KING); // false

            // Test 4: Testowanie avatarów
            boolean kingCanUseAnyAvatar = user1.canUseAvatar("prince_avatar_5"); // true

            // Test 5: Testowanie streak
            user1.updateStreak(true); // currentStreak = 1
            user1.updateStreak(true); // currentStreak = 2
            user1.updateStreak(false); // currentStreak = 1 (nowy streak)

            response.put("success", true);
            response.put("message", "Test modelu User przeszedł pomyślnie!");
            response.put("tests", Map.of(
                    "user1_created",
                    user1.getUsername() + " (" + user1.getRole().getDisplayName() + " " + user1.getRole().getEmoji()
                            + ")",
                    "king_vs_peasant", kingVsPeasant,
                    "peasant_vs_king", peasantVsKing,
                    "king_can_use_any_avatar", kingCanUseAnyAvatar,
                    "user1_current_streak", user1.getCurrentStreak(),
                    "user1_default_avatar", user1.getAvatarId()));

            return ResponseEntity.ok(response);

    }
}
