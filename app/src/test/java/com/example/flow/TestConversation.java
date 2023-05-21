package com.example.flow;

import static org.junit.Assert.assertEquals;

import com.example.flow.entities.Conversation;
import com.example.flow.entities.User;

import org.junit.Test;

public class TestConversation {
    @Test
    public void getSetPartTwoId() {
        Conversation conversation = new Conversation();
        conversation.setPartTwoId("emmanuel");
        String expected = "emmanuel";
        String output = conversation.getPartTwoId();
        assertEquals(expected,output);
    }
    @Test
    public void getSetPartyOneId() {
        Conversation conversation = new Conversation();
        conversation.setPartyOneId("emmanuel");
        String expected = "emmanuel";
        String output = conversation.getPartyOneId();
        assertEquals(expected,output);
    }
}
