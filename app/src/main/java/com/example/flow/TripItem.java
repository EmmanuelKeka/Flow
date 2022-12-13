package com.example.flow;

public class TripItem {
    private String form;
    private String to;
    private String imageName;
    private String driverName;
    private String driverId;
    private String tripDateTime;
    private String tripPrice;

    public TripItem(String form, String to, String imageName, String driverName, String driverId, String tripDateTime, String tripPrice) {
        this.form = form;
        this.to = to;
        this.imageName = imageName;
        this.driverName = driverName;
        this.driverId = driverId;
        this.tripDateTime = tripDateTime;
        this.tripPrice = tripPrice;
    }

    public String getTripDateTime() {
        return tripDateTime;
    }

    public String getTripPrice() {
        return tripPrice;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getForm() {
        return form;
    }

    public String getTo() {
        return to;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getImageName() {
        return imageName;
    }
}
