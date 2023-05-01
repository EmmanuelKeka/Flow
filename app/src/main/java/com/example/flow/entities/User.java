package com.example.flow.entities;

import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private AccountType accountType;
    private ArrayList<Conversation> conversations = new ArrayList<Conversation>();
    private ArrayList<Rate> driverRating = new ArrayList<Rate>();
    private ArrayList<Rate> passengerRating = new ArrayList<Rate>();
    private ArrayList<PendingRate> pendingRates = new ArrayList<PendingRate>();

    public User(String username, String email, AccountType accountType) {
        this.username = username;
        this.email = email;
        this.accountType = accountType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setConversations(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getUsername() {
        return username;
    }

    public User(){

    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public enum AccountType {
        DRIVER_ACCOUNT,
        PASSENGER_ACCOUNT
    }

    public ArrayList<Rate> getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(ArrayList<Rate> driverRating) {
        this.driverRating = driverRating;
    }

    public ArrayList<Rate> getPassengerRating() {
        return passengerRating;
    }

    public void setPassengerRating(ArrayList<Rate> passengerRating) {
        this.passengerRating = passengerRating;
    }

    public ArrayList<PendingRate> getPendingRates() {
        return pendingRates;
    }

    public void setPendingRates(ArrayList<PendingRate> pendingRates) {
        this.pendingRates = pendingRates;
    }
}
