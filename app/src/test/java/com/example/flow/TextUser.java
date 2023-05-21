package com.example.flow;

import static org.junit.Assert.assertEquals;

import com.example.flow.entities.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

public class TextUser {
    @Test
    public void getSetUsername() {
        User user = new User();
        user.setUsername("emmanuel");
        String expected = "emmanuel";
        String output = user.getUsername();
        assertEquals(expected,output);
    }
    @Test
    public void getSetEmail() {
        User user = new User();
        user.setEmail("emmanuel@gmail.com");
        String expected = "emmanuel@gmail.com";
        String output = user.getEmail();
        assertEquals(expected,output);
    }
    @Test
    public void getSetAccountType() {
        User user = new User();
        user.setAccountType(User.AccountType.DRIVER_ACCOUNT);
        User.AccountType expected = User.AccountType.DRIVER_ACCOUNT;
        User.AccountType output = user.getAccountType();
        assertEquals(expected,output);
    }
    @Test
    public void getSetUserName() {
        User user = new User();
        user.setAccountType(User.AccountType.DRIVER_ACCOUNT);
        User.AccountType expected = User.AccountType.DRIVER_ACCOUNT;
        User.AccountType output = user.getAccountType();
        assertEquals(expected,output);
    }
}
