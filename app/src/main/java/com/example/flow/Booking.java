package com.example.flow;

public class Booking {
    private String passagerId;
    private String driverId;
    private String from;
    private String to;
    private String bookingId;
    private String dateTime;
    private String driverName;
    private String price;


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
}
