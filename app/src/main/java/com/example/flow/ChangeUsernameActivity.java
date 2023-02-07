package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PublicKey;
import java.util.HashMap;

public class ChangeUsernameActivity extends AppCompatActivity {
    private EditText username;
    private TextView usernameView;
    private String userId, usernameText;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        username = findViewById(R.id.changeUsername);
        usernameText = getIntent().getStringExtra("username");
        usernameView = findViewById(R.id.currentUsername);
        usernameView.setText("Current Username: " + usernameText);
        bottomNavigationView = findViewById(R.id.nabBarChangeUsername);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
    }
    public void changeUsername(View view){
        if(!valideteInput()){
            return;
        }
        HashMap map = new HashMap();
        map.put("username",usernameText);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(userId)
                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{
                            System.out.println("nini lisusu");
                        }
                    }
                });
        NavigateToProfile();
    }
    public boolean valideteInput(){
        usernameText = username.getText().toString();
        if(usernameText.isEmpty()){
            username.setError("enter username");
            username.requestFocus();
            return false;
        }
        return true;
    }
    public void NavigateToProfile (){
        Intent intent = new Intent(this,ProfilActivity.class);
        startActivity(intent);
    }
}