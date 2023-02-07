package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flow.entities.User;
import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangeAccountTypeActivity extends AppCompatActivity {
    private String userId,acountType;
    private TextView accountTypeView;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_type);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent = getIntent();
        accountTypeView = findViewById(R.id.currentAcountType);
        acountType = intent.getStringExtra("accountType");
        accountTypeView.setText("Current Account Type: " + acountType);
        bottomNavigationView = findViewById(R.id.nabBarChangeAccount);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

    }
    public void changeToDriver(View view){
        HashMap userMap = new HashMap();
        userMap.put("accountType", User.AccountType.DRIVER_ACCOUNT);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(userId)
                .updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{
                            System.out.println("nini lisusu");
                        }
                    }
                });
        Intent intent = new Intent(this,ProfilActivity.class);
        startActivity(intent);
    }
    public void changeToPassger(View view){
        HashMap userMap = new HashMap();
        userMap.put("accountType", User.AccountType.PASSENGER_ACCOUNT);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(userId)
                .updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{
                            System.out.println("nini lisusu");
                        }
                    }
                });
        Intent intent = new Intent(this,ProfilActivity.class);
        startActivity(intent);
    }
}