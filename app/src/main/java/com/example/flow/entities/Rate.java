package com.example.flow.entities;

public class Rate {
    private int startNumber;
    private String raterId;
    private String ratedId;
    public Rate (){}

    public Rate(int startNumber, String raterId, String ratedId) {
        this.startNumber = startNumber;
        this.raterId = raterId;
        this.ratedId = ratedId;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public String getRaterId() {
        return raterId;
    }

    public void setRaterId(String raterId) {
        this.raterId = raterId;
    }

    public String getRatedId() {
        return ratedId;
    }

    public void setRatedId(String ratedId) {
        this.ratedId = ratedId;
    }
}
