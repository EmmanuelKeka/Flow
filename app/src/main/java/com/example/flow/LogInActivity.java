package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth auth;
    ProgressBar bar;
    LinearLayout logWin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.logInEmail);
        password = findViewById(R.id.logInPassword);
        bar = findViewById(R.id.LogInProgBar);
        logWin = findViewById(R.id.logInWindow);
    }
    public void logUser(View view){
        bar.setVisibility(View.VISIBLE);
        logWin.setVisibility(View.INVISIBLE);
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
                            bar.setVisibility(View.INVISIBLE);
                            logWin.setVisibility( View.VISIBLE);
                        }else{
                            bar.setVisibility(View.INVISIBLE);
                            logWin.setVisibility( View.VISIBLE);
                            Toast.makeText(LogInActivity.this, "Check your details and try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void sendToSignUpFromLogIn(View view){
        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
    }
    public void sentoForgot(View view){
        Intent intent = new Intent(getApplicationContext(),PasswordForgatActivity.class);
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