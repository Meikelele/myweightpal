package com.example.myweightpal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * we are generating/validating/extractdata tokens here
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;


    /**
     * base on my SECRET_KEY generates crypto key for users
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String role, String userId) {
        Map<String, Object> tickets = new HashMap<>();

        tickets.put("role", role);
        tickets.put("userId", userId);
        tickets.put("username", username);

        return createToken(tickets, username);
    }

    /**
     * creates token from provided data
     */
    private String createToken(Map<String, Object> tickets, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(tickets)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * token validation
     * if valid, expire or fake
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Nahh.. Invalid JWT Token: " + e.getMessage());
            return false;
        }
    }

    /**
     * That interface supports to extract data from token
     */
    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }

    /**
     * GENERIC
     * extract all data from token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * GENERIC
     * universal method to extracting data from token
     */
    public <T> T extractFromToken(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }


    public String extractUsername(String token) {
        return extractFromToken(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractFromToken(token, claims -> claims.get("role", String.class));
    }

    public String extractUserId(String token) {
        return extractFromToken(token, claims -> claims.get("userId", String.class));
    }

    public Date extractExpiration(String token) {
        return extractFromToken(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * for easieir debugging
     * printing all data about ticket (token)
     */
    public void printTokenInfo(String token) {
        try {
            Claims claims = extractAllClaims(token);
            System.out.println("=== TOKEN INFO ===");
            System.out.println("Username: " + claims.getSubject());
            System.out.println("Role: " + claims.get("role"));
            System.out.println("User ID: " + claims.get("userId"));
            System.out.println("Issued at: " + claims.getIssuedAt());
            System.out.println("Expires at: " + claims.getExpiration());
            System.out.println("Is expired: " + isTokenExpired(token));
            System.out.println("==================");
        } catch (Exception e) {
            System.err.println("Error reading token: " + e.getMessage());
        }
    }
}
