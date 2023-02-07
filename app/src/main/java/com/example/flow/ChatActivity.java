package com.example.flow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private String userId;
    private MenuSetter menuSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarChat);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
    }
}