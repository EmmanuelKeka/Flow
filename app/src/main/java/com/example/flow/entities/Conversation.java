package com.example.flow.entities;

import java.util.ArrayList;

public class Conversation {
    ArrayList<MessageRead> messages = new ArrayList<MessageRead>();
    String partyOneId;
    String PartTwoId;

    public ArrayList<MessageRead> getMessages() {
        return messages;
    }

    public void addMessage(MessageRead messageRead){
        messages.add(messageRead);
    }

    public void setMessages(ArrayList<MessageRead> messages) {
        this.messages = messages;
    }

    public String getPartyOneId() {
        return partyOneId;
    }

    public void setPartyOneId(String partyOneId) {
        this.partyOneId = partyOneId;
    }

    public Conversation(String partyOneId, String partTwoId) {
        this.partyOneId = partyOneId;
        PartTwoId = partTwoId;
    }
    public Conversation() {

    }

    public String getPartTwoId() {
        return PartTwoId;
    }

    public void setPartTwoId(String partTwoId) {
        PartTwoId = partTwoId;
    }
}
