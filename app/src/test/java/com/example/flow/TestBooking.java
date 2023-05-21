package com.example.flow;

import static junit.framework.Assert.assertEquals;

import com.example.flow.entities.Booking;

import org.junit.Test;

public class TestBooking {
    @Test
    public void getSetPassagerId() {
        Booking booking = new Booking();
        booking.setPassagerId("123");
        String expected = "123";
        String output = booking.getPassagerId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetDriverId() {
        Booking booking = new Booking();
        booking.setDriverId("123");
        String expected = "123";
        String output = booking.getDriverId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetFrom() {
        Booking booking = new Booking();
        booking.setFrom("123");
        String expected = "123";
        String output = booking.getFrom();
        assertEquals(expected,output);
    }
    @Test
    public void getSetTo() {
        Booking booking = new Booking();
        booking.setTo("123");
        String expected = "123";
        String output = booking.getTo();
        assertEquals(expected,output);
    }
    @Test
    public void getSetBookingId() {
        Booking booking = new Booking();
        booking.setBookingId("123");
        String expected = "123";
        String output = booking.getBookingId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetDateTime() {
        Booking booking = new Booking();
        booking.setDateTime("123");
        String expected = "123";
        String output = booking.getDateTime();
        assertEquals(expected,output);
    }
    @Test
    public void getSetDriverName() {
        Booking booking = new Booking();
        booking.setDriverName("123");
        String expected = "123";
        String output = booking.getDriverName();
        assertEquals(expected,output);
    }
    @Test
    public void getSetPrice() {
        Booking booking = new Booking();
        booking.setPrice("123");
        String expected = "123";
        String output = booking.getPrice();
        assertEquals(expected,output);
    }
    @Test
    public void getSetTripId() {
        Booking booking = new Booking();
        booking.setTripId("123");
        String expected = "123";
        String output = booking.getTripId();
        assertEquals(expected,output);
    }
}
