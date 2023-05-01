package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.flow.entities.PendingRate;
import com.example.flow.entities.Rate;
import com.example.flow.entities.User;
import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class RatingActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MenuSetter menuSetter;
    String userId;
    String driverId = "";
    String passengerId = "";
    String index = "";
    AutoCompleteTextView autoCompleteTextView;
    String [] startsNumbers = {"1","2","3","4","5"};
    boolean driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarRating);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);

        autoCompleteTextView = findViewById(R.id.accountTypeSelector);
        menuSetter.setMenu();
        Intent intent = getIntent();
        index = intent.getStringExtra("Index");
        driverId = intent.getStringExtra("driverId");
        passengerId = intent.getStringExtra("PassengerId");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.account_time_item,startsNumbers);
        autoCompleteTextView.setAdapter(adapter);
    }
    public void saveMessageForPartyTwo(ArrayList<Rate> rates,String id){
        if(driver) {
            FirebaseDatabase.getInstance().getReference("Users")
                    .child(id)
                    .child("driverRating")
                    .setValue(rates);
        }
        else{
            FirebaseDatabase.getInstance().getReference("Users")
                    .child(id)
                    .child("passengerRating")
                    .setValue(rates);
        }
        loadPartyTwoConversation();
    }
    public void rateUser(View view){
        if(autoCompleteTextView.getText().toString().isEmpty()){
            Toast.makeText(this, "Select number of Starts", Toast.LENGTH_SHORT).show();
            return;
        }
        int numberofstart = Integer.parseInt(autoCompleteTextView.getText().toString());
        String id = "";
        Rate rating = new Rate();
        if(driverId.equals(userId)){
            driver = false;
            id = passengerId;
        }
        else {
            driver = true;
            id = driverId;
        }
        rating.setRatedId(id);
        rating.setRaterId(userId);
        rating.setStartNumber(numberofstart);
        loadPasRating(rating,id);
        startActivity(new Intent(this, ProfilActivity.class));
    }
    public void loadPasRating(Rate rating,String id){
        FirebaseDatabase.getInstance().getReference("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    ArrayList<Rate> rates = null;
                    if(driver){
                        rates = userPro.getDriverRating();
                    }
                    else{
                        rates = userPro.getPassengerRating();
                    }
                    rates.add(rating);
                    saveMessageForPartyTwo(rates, id);
                }
                else{
                    System.out.println("nini papa" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addToPeddingList(ArrayList<PendingRate> pendingRates, String id){
        FirebaseDatabase.getInstance().getReference("Users")
                .child(id)
                .child("pendingRates")
                .setValue(pendingRates);
    }
    public void loadPartyTwoConversation(){
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    ArrayList<PendingRate> pendingRates = userPro.getPendingRates();
                    pendingRates.remove(Integer.parseInt(index));
                    addToPeddingList(pendingRates,userId);
                }
                else{
                    System.out.println("nini papa" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}