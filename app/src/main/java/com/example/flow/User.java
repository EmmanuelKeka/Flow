package com.example.flow;

public class User {
    private String username;
    private String email;
    private AccountType accountType;

    public User(String username, String email, AccountType accountType) {
        this.username = username;
        this.email = email;
        this.accountType = accountType;
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
    enum AccountType {
        DRIVER_ACCOUNT,
        PASSGER_ACCOUNT
    }
}
