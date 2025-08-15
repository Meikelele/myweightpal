package com.example.myweightpal.controller;

import com.example.myweightpal.dto.LoginRequest;
import com.example.myweightpal.dto.LoginResponse;
import com.example.myweightpal.model.User;
import com.example.myweightpal.repository.UserRepository;
import com.example.myweightpal.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtUtil jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        var user = users.findByEmail(req.email()).orElse(null);
        if (user == null || !encoder.matches(req.password(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
        String token = jwt.generateToken(user.getUsername(), user.getRole().name(), user.getId());
        return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getEmail(), user.getRole().name()));
    }
}
