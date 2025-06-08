package com.example.myweightpal.dto;

import com.example.myweightpal.model.WeightEntry;

public class WeightEntryResponse {
    public int id;
    public int userId;
    public String date;
    public double weight;
    public boolean tookCreatine;
    public String note;

    public static WeightEntryResponse from(WeightEntry entry) {
        WeightEntryResponse r = new WeightEntryResponse();
        r.id = entry.getId();
        r.userId = entry.getUserId();
        r.date = entry.getDate().toString();
        r.weight = entry.getWeight();
        r.tookCreatine = entry.isTookCreatine();
        r.note = entry.getNote();
        return r;
    }
}
