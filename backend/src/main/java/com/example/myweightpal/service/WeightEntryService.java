package com.example.myweightpal.service;

import com.example.myweightpal.model.WeightEntry;
import com.example.myweightpal.repository.WeightEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WeightEntryService {
    private final WeightEntryRepository weightRepo;

    public WeightEntryService(WeightEntryRepository weightRepo) {
        this.weightRepo = weightRepo;
    }

    /**
     * add weightentry to database
     */
    public WeightEntry addWeightEntry(WeightEntry entry) {
        // TODO: change translations for errors

        // User cannot add WeigghtEntry from future
        LocalDate today = LocalDate.now();
        if (entry.getDate().isAfter(today)) { throw new IllegalArgumentException("Date cannot be in future."); };

        // User cannot have two weight entries the same day
        Optional<WeightEntry> existing = weightRepo.getWeightEntryByUserIdAndDate(entry.getUserId(), entry.getDate());
        if (existing.isPresent()) { throw new IllegalStateException("Entry for that day already exists."); }

        return weightRepo.save(entry);
    }

    /**
     * get previous weught entry
     */
    public Optional<WeightEntry> findLatestWeightEntryForUser(int userId) {
        List<WeightEntry> latestFoundEntry = weightRepo.findByUserIdOrderByDateAsc(userId);

        return latestFoundEntry.isEmpty() ? Optional.empty() : Optional.of(latestFoundEntry.get(latestFoundEntry.size() - 1));
    }

    /**
     *
     */
    public List<WeightEntry> findByUserId(int userId) {
        return weightRepo.findByUserIdOrderByDateAsc(userId);
    }
}
