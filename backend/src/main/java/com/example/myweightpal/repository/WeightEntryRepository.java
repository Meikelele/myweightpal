package com.example.myweightpal.repository;

import com.example.myweightpal.model.WeightEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeightEntryRepository extends MongoRepository<WeightEntry, String> {
    List<WeightEntry> findByUserIdOrderByDateAsc(int userId);

    Optional<WeightEntry> getWeightEntryByUserIdAndDate(int userId, LocalDate date);
}
