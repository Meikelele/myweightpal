package com.example.myweightpal.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed
    private String username;

    private String password;

    @Indexed
    private String email;

    private String nickname;

    private Role role;

    private String avatarId;

    private String language;

    private String theme;

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    private boolean isActive;

    private int currentStreak;
    private int longestStreak;
    private int totalEntries;
    // TODO: creatine fields
    // some fields for represant creatine streak


    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;

        this.nickname = username;
        this.language = "PL";
        this.theme = "light";
        this.avatarId = getDefaultAvatarForRole(role);
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.totalEntries = 0;

    }

    private String getDefaultAvatarForRole(Role role) {
        switch (role) {
            case KING:
                return "king_avatar_1";
            case PRINCE:
                return "prince_avatar_1";
            case PEASANT:
                return "peasant_avatar_1";
            default:
                return "default_avatar";
        }
    }

    public boolean hasHigherRankThan(Role otherRole) {
        return this.role.hasHigherRankThan(otherRole);
    }

    public boolean canUseAvatar(String avatarId) {
        if (this.role == Role.KING) {
            return true;
        }

        if (this.role == Role.PRINCE) {
            return avatarId.startsWith("prince_") || avatarId.startsWith("peasant_");
        }

        if (this.role == Role.PEASANT) {
            return avatarId.startsWith("peasant_");
        }

        return false;
    }

    public void updateStreak(boolean continuedStreak) {
        if (continuedStreak) {
            this.currentStreak++;
            if (this.currentStreak > this.longestStreak) {
                this.longestStreak = this.currentStreak;
            }
        } else {
            this.currentStreak = 1;
        }
    }

    public void incrementTotalEntries() {
        this.totalEntries++;
    }

    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public void setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
    }
}
