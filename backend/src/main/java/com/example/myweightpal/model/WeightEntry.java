package com.example.myweightpal.model;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@Document(collection = "weight_entries")
public class WeightEntry {
    private int id;
    private int userId;
    private LocalDate date;
    private String note;

    private boolean tookCreatine;
    private double weight;

    public WeightEntry() { }          // wymagany przez Spring Data

    public WeightEntry(int userId, LocalDate date, double weight, boolean tookCreatine, String note) {
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.tookCreatine = tookCreatine;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isTookCreatine() {
        return tookCreatine;
    }

    public void setTookCreatine(boolean tookCreatine) {
        this.tookCreatine = tookCreatine;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "WeightEntry{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", date=" + date +
                ", weight=" + weight +
                ", tookCreatine=" + tookCreatine +
                ", note='" + note + '\'' +
                '}';
    }
}
