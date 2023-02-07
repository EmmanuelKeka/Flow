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

public class SearchResultActivity extends AppCompatActivity {
    ListView listView;
    String date,from,to,userId;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;
    MenuSetter menuSetter;
    ArrayList<Trip> trips = new ArrayList<Trip>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        listView = findViewById(R.id.ListTripSearchResult);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarSearchResult);

        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

        reference = FirebaseDatabase.getInstance().getReference("Trips");
        getTrips();
    }
    public void GoToSearchFromSearchResult(View view){
        Intent intent = new Intent(this,SearchResultActivity.class);
        startActivity(intent);

    }
    public void getTrips(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Trip trip = ds.getValue(Trip.class);
                    if(trip !=null) {
                        String tripDate = trip.getDateTime().toString().split(" ")[0];
                        if(trip.getFrom().equals(from) && trip.getTo().equals(to)&& tripDate.equals(date))
                            trips.add(trip);
                    }

                }
                setListApdaper();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setListApdaper(){
        tripAdapter adapter = new tripAdapter(SearchResultActivity.this, R.layout.ride_item, trips);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayTripOnRideView(i);
            }
        });
    }
    public void displayTripOnRideView(int i){
        Trip tripItem = trips.get(i);
        Intent intent = new Intent(SearchResultActivity.this, RideViewActivity.class);
        intent.putExtra("from", tripItem.getFrom());
        intent.putExtra("to", tripItem.getTo());
        intent.putExtra("DriverName", tripItem.getDriverName());
        intent.putExtra("ImageName", tripItem.getDriverId());
        intent.putExtra("DriverId", tripItem.getDriverId());
        intent.putExtra("TripDateTime", tripItem.getDateTime());
        intent.putExtra("TripPrice", tripItem.getPrice());
        intent.putExtra("PassagerId", userId);
        startActivity(intent);
    }
}