package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.flow.listAdapers.tripAdapter;
import com.example.flow.entities.Trip;
import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RideScreenActivity extends AppCompatActivity {
    ListView listView;
    DatabaseReference reference;
    String userId;
    BottomNavigationView bottomNavigationView;
    MenuSetter menuSetter;
    ArrayList<Trip> trips = new ArrayList<Trip>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_screen);

        reference = FirebaseDatabase.getInstance().getReference("Trips");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarRideScreen);

        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listView = findViewById(R.id.ListTripRideList);
                for(DataSnapshot ds: snapshot.getChildren()){
                    Trip trip= ds.getValue(Trip.class);
                    if(trip.getDriverId().equals(userId)){
                        continue;
                    }
                    if(!trip.isTripBook()) {
                        trips.add(trip);
                    }
                }
                tripAdapter adapter = new tripAdapter(RideScreenActivity.this,R.layout.ride_item,trips);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        displayTripOnRideScreen(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void GoToSearchFromTrips(View view){
        Intent intent = new Intent(this, RideSearchActivity.class);
        startActivity(intent);
    }
    public void displayTripOnRideScreen(int i){
        Trip tripItem = trips.get(i);
        Intent intent = new Intent(RideScreenActivity.this,RideViewActivity.class);
        intent.putExtra("from",tripItem.getFrom());
        intent.putExtra("to",tripItem.getTo());
        intent.putExtra("DriverName",tripItem.getDriverName());
        intent.putExtra("ImageName",tripItem.getDriverId());
        intent.putExtra("DriverId",tripItem.getDriverId());
        intent.putExtra("TripDateTime",tripItem.getDateTime());
        intent.putExtra("TripPrice",tripItem.getPrice());
        intent.putExtra("TripId",tripItem.getTripId());
        intent.putExtra("PassagerId" , userId);
        startActivity(intent);
    }

}