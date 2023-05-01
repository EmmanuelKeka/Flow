package com.example.flow.entities;

public class Booking {
    private String passagerId;
    private String driverId;
    private String from;
    private String to;
    private String bookingId;
    private String dateTime;
    private String driverName;
    private String price;
    private String tripId;


    public Booking(String passagerId, String driverId, String from, String to, String bookingId, String dateTime, String driverName, String price) {
        this.passagerId = passagerId;
        this.driverId = driverId;
        this.from = from;
        this.to = to;
        this.bookingId = bookingId;
        this.dateTime = dateTime;
        this.driverName = driverName;
        this.price = price;
    }
    public Booking(){

    }

    public String getPrice() {
        return price;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPassagerId() {
        return passagerId;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setPassagerId(String passagerId) {
        this.passagerId = passagerId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
