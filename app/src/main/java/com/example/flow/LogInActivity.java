package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.logInEmail);
        password = findViewById(R.id.logInPassword);
    }
    public void logUser(View view){
        if(!validateInput()){
            return;
        }
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        auth.signInWithEmailAndPassword(emailText,passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogInActivity.this, "log in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
    public void sendToSignUpFromLogIn(View view){
        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
    }
    public boolean validateInput(){
        if(email.getText().toString().isEmpty()){
            email.setError("Enter email");
            email.requestFocus();
            return false;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Enter password");
            password.requestFocus();
            return false;
        }
        return true;
    }
}