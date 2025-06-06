package com.example.myweightpal.model;

public enum Role {
    KING("King", 3, "👑"),
    PRINCE("Prince", 2, "🤴"),
    PEASANT("Peasant", 1, "👨‍🌾");

    private final String displayName;
    private final int level;
    private final String emoji;

    Role(String displayName, int level, String emoji) {
        this.displayName = displayName;
        this.level = level;
        this.emoji = emoji;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    public String getEmoji() {
        return emoji;
    }

    /**
     * to compare two ranks / roles
     */
    public boolean hasHigherRankThan(Role other) {
        return this.getLevel() > other.getLevel();
    }
}