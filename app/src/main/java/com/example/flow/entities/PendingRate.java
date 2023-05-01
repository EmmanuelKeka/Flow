package com.example.flow.entities;

public class PendingRate {
    private String DriverId;
    private String PassengerId;

    PendingRate(){

    }

    public PendingRate(String raterId, String PassengerId) {
        this.DriverId = raterId;
        this.PassengerId = PassengerId;
    }

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        this.DriverId = driverId;
    }

    public String getPassengerId() {
        return PassengerId;
    }

    public void setPassengerId(String passengerId) {
        this.PassengerId = passengerId;
    }
}
