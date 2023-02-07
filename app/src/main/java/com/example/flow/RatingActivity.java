package com.example.flow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class RatingActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MenuSetter menuSetter;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarRating);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
    }
}