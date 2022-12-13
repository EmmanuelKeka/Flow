package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email,password,username;
    AutoCompleteTextView autoCompleteTextView;
    String [] accountTypes = {User.AccountType.DRIVER_ACCOUNT.toString(),User.AccountType.PASSGER_ACCOUNT.toString()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        username = findViewById(R.id.signUpUsername);
        autoCompleteTextView = findViewById(R.id.accountTypeSelector);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.account_time_item,accountTypes);
        autoCompleteTextView.setAdapter(adapter);

    }
    public void creatUser(View view){
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String usernameText = username.getText().toString();
        if(emailText.isEmpty()){
            email.setError("Enter email");
            email.requestFocus();
            return;
        }
        if(emailText.isEmpty()){
            username.setError("Enter Username");
            username.requestFocus();
            return;
        }
        if(passwordText.isEmpty()){
            password.setError("Enter password");
            password.requestFocus();
            return;
        }
        if(passwordText.length() < 8){
            password.setError("pasword too short");
            password.requestFocus();
            return;
        }
        auth.createUserWithEmailAndPassword(emailText,passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User.AccountType accountType = null;
                            System.out.println(autoCompleteTextView.getText().toString());
                            if(User.AccountType.DRIVER_ACCOUNT.toString().equals(autoCompleteTextView.getText().toString()))
                                accountType = User.AccountType.DRIVER_ACCOUNT;
                            else
                                accountType = User.AccountType.PASSGER_ACCOUNT;

                            User user = new User(usernameText,emailText, accountType);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                            }
                                            else{
                                                System.out.println("nini lisusu");
                                            }
                                        }
                                    });
                            email.setText("");
                            password.setText("");
                            username.setText("");
                            Toast.makeText(SignUpActivity.this, "Sign Up uccessfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void SendToLogInFromSignUp(View view){
        Intent intent = new Intent(this,LogInActivity.class);
        startActivity(intent);
    }
}