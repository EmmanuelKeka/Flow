package com.example.flow;

import static junit.framework.Assert.assertEquals;

import com.example.flow.entities.MessageRead;

import org.junit.Test;

public class TestMessage {
    @Test
    public void getSetMessage() {
        MessageRead message = new MessageRead();
        message.setMessage("hi");
        String expected = "hi";
        String output =message.getMessage();
        assertEquals(expected,output);
    }
    @Test
    public void getSetSenderID() {
        MessageRead message = new MessageRead();
        message.setSenderID("123");
        String expected = "123";
        String output =message.getSenderID();
        assertEquals(expected,output);
    }
}
