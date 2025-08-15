package com.example.myweightpal.dto;

public record LoginResponse(
        String accessToken,
        String userId,
        String email,
        String role
) {}
