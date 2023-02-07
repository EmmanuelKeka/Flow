package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.flow.listAdapers.BookingAdaptor;
import com.example.flow.entities.Booking;
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

public class TripPostedActivity extends AppCompatActivity {
    ListView listView;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;
    String userId;
    MenuSetter menuSetter;
    ArrayList<Booking> tripItems = new ArrayList<Booking>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_posted);

        listView = findViewById(R.id.PostTripList);
        reference = FirebaseDatabase.getInstance().getReference("Trips");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarTripPosted);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Trip book= ds.getValue(Trip.class);

                    if(book.getDriverId().equals(userId))
                        tripItems.add(new Booking("", book.getDriverId(), book.getFrom(), book.getTo(), book.getTripId(), book.getDateTime(), book.getDriverName(), book.getPrice()));

                }
                setAdaperAndList();

                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setAdaperAndList(){
        BookingAdaptor adapter = new BookingAdaptor (getApplicationContext(),R.layout.book_item,tripItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayTripOnTripPostView(i);
            }
        });
    }
    public void displayTripOnTripPostView(int i){
        Booking booking = tripItems.get(i);
        Intent intent = new Intent(getApplicationContext(),TripPostViewActivity.class);
        intent.putExtra("from",booking.getFrom());
        intent.putExtra("to",booking.getTo());
        intent.putExtra("DriverName",booking.getDriverName());
        intent.putExtra("DriverId",booking.getDriverId());
        intent.putExtra("TripDateTime",booking.getDateTime());
        intent.putExtra("TripPrice",booking.getPrice());
        intent.putExtra("TripId",booking.getBookingId());
        startActivity(intent);
    }
}