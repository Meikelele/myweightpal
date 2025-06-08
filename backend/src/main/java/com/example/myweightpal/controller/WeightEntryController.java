package com.example.myweightpal.controller;


import com.example.myweightpal.dto.WeightEntryRequest;
import com.example.myweightpal.dto.WeightEntryResponse;
import com.example.myweightpal.model.WeightEntry;
import com.example.myweightpal.service.WeightEntryService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/weights")
public class WeightEntryController {

    private final WeightEntryService weightEntryService;

    public WeightEntryController(WeightEntryService weightEntryService) {
        this.weightEntryService = weightEntryService;
    }

    @PostMapping
    public ResponseEntity<?> addWeightEntry(@RequestBody WeightEntryRequest request) {
        try {
            WeightEntry entry = new WeightEntry(
              request.userId,
              LocalDate.parse(request.date),
              request.weight,
              request.tookCreatine,
              request.note
            );

            WeightEntry saved = weightEntryService.addWeightEntry(entry);
            return ResponseEntity.ok(WeightEntryResponse.from(saved));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public List<WeightEntryResponse> getAllWeightEntriesForUser(@PathVariable int userId) {
        return weightEntryService.findLatestWeightEntryForUser(userId).stream().map(WeightEntryResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{userId}/latest")
    public ResponseEntity<?> getLatestWeightEntryForUser(@PathVariable int userId) {
        return weightEntryService.findLatestWeightEntryForUser(userId).
                <ResponseEntity<?>>map(e -> ResponseEntity.ok(WeightEntryResponse.from(e))).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
