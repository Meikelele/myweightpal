package com.example.myweightpal.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.myweightpal.model.Role;
import com.example.myweightpal.model.User;
import com.example.myweightpal.model.WeightEntry;
import com.example.myweightpal.service.WeightEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    // Dodaj tę metodę do swojego TestController.java

    private final WeightEntryService weightEntryService;
    public TestController(WeightEntryService weightEntryService) {
        this.weightEntryService = weightEntryService;
    }


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

    @GetMapping("/entry")
    public ResponseEntity<Map<String, Object>> testWeightEntry() {
        Map<String, Object> response = new HashMap<>();

        WeightEntry e1 = new WeightEntry(
                1,                      // userId
                LocalDate.now(),              // dzisiaj
                82.4,
                true,                         // brał kreatynę
                "Trening nóg"
        );

        WeightEntry e2 = new WeightEntry(
                1,
                LocalDate.now().minusDays(1), // wczoraj
                82.9,
                false,
                "Rest day"
        );

        response.put("success", true);
        response.put("entries", List.of(e1, e2));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/entry")
    public ResponseEntity<?> testAddEntry(@RequestBody WeightEntry entry) {

        try {
            WeightEntry saved = weightEntryService.addWeightEntry(entry);
            return ResponseEntity.ok(saved);          // 200 + zapisany obiekt
        } catch (Exception ex) {
            Map<String, Object> err = Map.of(
                    "error", ex.getClass().getSimpleName(),
                    "message", ex.getMessage()
            );
            return ResponseEntity.badRequest().body(err);
        }
    }


}
