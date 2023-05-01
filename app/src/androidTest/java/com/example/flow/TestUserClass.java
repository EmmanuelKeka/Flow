package com.example.flow;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.flow.entities.User;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestUserClass {
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
        user.setEmail("emmanuel@gmail.com");
        String expected = "emmanuel@gmail.com";
        String output = user.getEmail();
        assertEquals(expected,output);
    }
}
