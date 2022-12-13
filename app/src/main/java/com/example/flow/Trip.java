package com.example.flow;

public class Trip {
    private String from;
    private String to;
    private String driverName;
    private String driverId;
    private String dateTime;
    private String price;
    private String tripId;


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
}
