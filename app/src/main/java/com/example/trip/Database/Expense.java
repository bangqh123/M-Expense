package com.example.trip.Database;

import java.io.Serializable;

public class Expense implements Serializable {
    private int id;
    private String type;
    private String date;
    private int amount;
    private String comment;
    private int tripId;

    public Expense(int id, String type, String date, int amount, String comment, int tripId) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.tripId = tripId;
    }

    public Expense(String type, String date, int amount, String comment, int tripId) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
        this.tripId = tripId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}
