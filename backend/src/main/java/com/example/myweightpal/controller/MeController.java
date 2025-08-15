package com.example.myweightpal.controller;

import com.example.myweightpal.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MeController {
    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        User u = (User) auth.getPrincipal();
        return Map.of(
                "id", u.getId(),
                "email", u.getEmail(),
                "username", u.getUsername(),
                "role", u.getRole().name()
        );
    }
}
