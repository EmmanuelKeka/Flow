package com.example.flow;

import static junit.framework.Assert.assertEquals;

import com.example.flow.entities.Booking;
import com.example.flow.entities.Trip;

import org.junit.Test;

public class TestTrip {
    private String from;
    private String to;
    private String driverName;
    private String driverId;
    private String dateTime;
    private String price;
    private String tripId;
    private boolean tripBook = false;
    @Test
    public void getSetFrom() {
        Trip trip = new Trip();
        trip.setFrom("123");
        String expected = "123";
        String output = trip.getFrom();
        assertEquals(expected,output);
    }
    @Test
    public void getSetTo() {
        Trip trip = new Trip();
        trip.setTo("123");
        String expected = "123";
        String output = trip.getTo();
        assertEquals(expected,output);
    }
    @Test
    public void getSetDriverName() {
        Trip trip = new Trip();
        trip.setDriverName("123");
        String expected = "123";
        String output = trip.getDriverName();
        assertEquals(expected,output);
    }
    @Test
    public void getSetDriverId() {
        Trip trip = new Trip();
        trip.setDriverId("123");
        String expected = "123";
        String output = trip.getDriverId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetDateTime() {
        Trip trip = new Trip();
        trip.setDateTime("123");
        String expected = "123";
        String output = trip.getDateTime();
        assertEquals(expected,output);
    }
    @Test
    public void getSetPrice() {
        Trip trip = new Trip();
        trip.setPrice("123");
        String expected = "123";
        String output = trip.getPrice();
        assertEquals(expected,output);
    }
    @Test
    public void getSetTripId() {
        Trip trip = new Trip();
        trip.setTripId("123");
        String expected = "123";
        String output = trip.getTripId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetTripBook() {
        Trip trip = new Trip();
        trip.setTripBook(false);
        boolean expected = false;
        boolean output = trip.isTripBook();
        assertEquals(expected,output);
    }
}
