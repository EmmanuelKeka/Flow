package com.example.flow;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordForgatActivity extends AppCompatActivity {
    private EditText email;
    private ProgressBar bar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgat);
        email = findViewById(R.id.logInEmailPasswordForgot);
        auth = FirebaseAuth.getInstance();
    }
    public void sentoSignInfromForgot(View view){
        Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
        startActivity(intent);
    }
    public void sendLink(View view){
        String emailText = email.getText().toString().trim();
        if(emailText.isEmpty()){
            email.requestFocus();
            email.setError("please enter email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.requestFocus();
            email.setError("please enter a valid email");
            return;
        }
        auth.sendPasswordResetEmail(emailText);
        Toast.makeText(this, "Reset Password email will be sent if account exist", Toast.LENGTH_SHORT).show();
    }
}