package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flow.entities.Conversation;
import com.example.flow.entities.PendingRate;
import com.example.flow.entities.User;
import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BookedTripViewActivity extends AppCompatActivity {
    private TextView driverName,dateTime,from,to,price;
    private DatabaseReference reference;
    private String bookingIdText,userId,driverId,tripid,passengerId, toSendId;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_trip_view);

        driverName = findViewById(R.id.BookedViewDriverName);
        dateTime = findViewById(R.id.BookedViewDateTime);
        from = findViewById(R.id.BookedViewFrom);
        to = findViewById(R.id.BookedViewTo);
        price = findViewById(R.id.BookedViewPrice);
        reference = FirebaseDatabase.getInstance().getReference("Booking");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarBookedTripView);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

        Intent intent = getIntent();
        String driverNameText = intent.getStringExtra("DriverName");
        String dateTimeText = intent.getStringExtra("TripDateTime");
        String fromText = intent.getStringExtra("from");
        String toText = intent.getStringExtra("to");
        String priceText = intent.getStringExtra("TripPrice");
        bookingIdText = intent.getStringExtra("BookingId");
        driverId = intent.getStringExtra("DriverId");
        tripid = intent.getStringExtra("TripId");
        passengerId = intent.getStringExtra("PasId");

        if(driverId.equals(userId)){
            toSendId = passengerId;
            FirebaseDatabase.getInstance().getReference("Users").child(passengerId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userPro = snapshot.getValue(User.class);
                    if(userPro !=null){
                        driverName.setText("Passenger Name: " + userPro.getUsername());
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
        else {
            toSendId = driverId;
            driverName.setText("Driver Name: " + driverNameText);
        }
        dateTime.setText("Date & Time: "+ dateTimeText);
        from.setText("From: " + fromText);
        to.setText("To: "+ toText);
        price.setText("Price: "+ priceText);


    }
    public void cancelBookedTrip(View view){
        deleteBooking();
        bookTrip();
    }
    public void completeTrip(View view){
        addPandingMain();
        loadPartyTwoConversation();
    }
    public void deleteBooking(){
        reference.child(bookingIdText)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(),BookedTripActivity.class);
                        startActivity(intent);
                    }
                });
    }

    public void bookTrip(){
        HashMap map = new HashMap();
        map.put("tripBook",false);
        FirebaseDatabase.getInstance().getReference("Trips")
                .child(tripid)
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
    }

    public void addToPeddingList(ArrayList<PendingRate> pendingRates,String id){
        FirebaseDatabase.getInstance().getReference("Users")
                .child(id)
                .child("pendingRates")
                .setValue(pendingRates);
        deleteBooking();
    }
    public void addPandingMain(){
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    ArrayList<PendingRate> pendingRates = userPro.getPendingRates();
                    pendingRates.add(new PendingRate(userId, driverId));
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
    public void loadPartyTwoConversation(){
        FirebaseDatabase.getInstance().getReference("Users").child(driverId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    ArrayList<PendingRate> pendingRates = userPro.getPendingRates();
                    pendingRates.add(new PendingRate(userId, driverId));
                    addToPeddingList(pendingRates,driverId);
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
    public void messagerOtherParty(View view){
        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra("otherParty",toSendId);
        startActivity(intent);
    }
}