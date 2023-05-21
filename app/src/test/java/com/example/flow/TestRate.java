package com.example.flow;

import static junit.framework.Assert.assertEquals;

import com.example.flow.entities.Rate;

import org.junit.Test;

public class TestRate {
    @Test
    public void getSetMessage() {
        Rate rate = new Rate();
        rate.setStartNumber(1);
        int expected = 1;
        int output =rate.getStartNumber();
        assertEquals(expected,output);
    }
    @Test
    public void getSetRaterId() {
        Rate rate = new Rate();
        rate.setRaterId("123");
        String expected = "123";
        String output =rate.getRaterId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetRatedId() {
        Rate rate = new Rate();
        rate.setRatedId("123");
        String expected = "123";
        String output =rate.getRatedId();
        assertEquals(expected,output);
    }
}
