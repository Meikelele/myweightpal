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

}
