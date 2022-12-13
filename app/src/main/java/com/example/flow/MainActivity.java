package com.example.flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);
        }
    }
    public void SendToSignUpFromMain(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    public void SendToLogInFromMain(View view){
        Intent intent = new Intent(this,LogInActivity.class);
        startActivity(intent);
    }
}