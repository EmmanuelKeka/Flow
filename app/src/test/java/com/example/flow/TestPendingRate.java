package com.example.flow;

import static junit.framework.Assert.assertEquals;

import com.example.flow.entities.MessageRead;
import com.example.flow.entities.PendingRate;

import org.junit.Test;


public class TestPendingRate {
    @Test
    public void getSetDriverId() {
        PendingRate pendingRate = new PendingRate();
        pendingRate.setDriverId("123");
        String expected = "123";
        String output = pendingRate.getDriverId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetPassengerId() {
        PendingRate pendingRate = new PendingRate();
        pendingRate.setPassengerId("123");
        String expected = "123";
        String output = pendingRate.getPassengerId();
        assertEquals(expected,output);
    }
}
