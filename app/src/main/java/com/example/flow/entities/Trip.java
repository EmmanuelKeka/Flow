package com.example.flow.entities;

public class Trip {
    private String from;
    private String to;
    private String driverName;
    private String driverId;
    private String dateTime;
    private String price;
    private String tripId;
    private boolean tripBook = false;


    public Trip(String from, String to, String driverName, String driverId, String dateTime, String price, String tripId) {
        this.from = from;
        this.to = to;
        this.driverName = driverName;
        this.driverId = driverId;
        this.dateTime = dateTime;
        this.price = price;
        this.tripId = tripId;
    }

    public Trip(){

    }

    public String getTripId() {
        return tripId;
    }

    public String getPrice() {
        return price;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public boolean isTripBook() {
        return tripBook;
    }

    public void setTripBook(boolean tripBook) {
        this.tripBook = tripBook;
    }

}
