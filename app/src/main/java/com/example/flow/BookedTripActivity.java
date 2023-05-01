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
import com.example.flow.entities.User;
import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookedTripActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private String userId;
    private ListView listView;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;
    ArrayList<Booking> bookings = new ArrayList<Booking>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_trip);

        bottomNavigationView = findViewById(R.id.nabBarBookedTrip);
        reference = FirebaseDatabase.getInstance().getReference("Booking");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        listView = findViewById(R.id.BookedTripList);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
        loadTripbyAccountType();
    }
    public void loadTrip(User.AccountType acountType ){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Booking book= ds.getValue(Booking.class);
                    checkAcountTyepeAddBookingToArray(acountType,book);
                }
                setListBookingListener();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setListBookingListener(){
        BookingAdaptor adapter = new BookingAdaptor (getApplicationContext(),R.layout.book_item,bookings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Booking booking = bookings.get(i);
                Intent intent = new Intent(getApplicationContext(),BookedTripViewActivity.class);
                intent.putExtra("from",booking.getFrom());
                intent.putExtra("to",booking.getTo());
                intent.putExtra("DriverName",booking.getDriverName());
                intent.putExtra("DriverId",booking.getDriverId());
                intent.putExtra("TripDateTime",booking.getDateTime());
                intent.putExtra("TripPrice",booking.getPrice());
                intent.putExtra("DriverId",booking.getDriverId());
                intent.putExtra("BookingId",booking.getBookingId());
                intent.putExtra("TripId",booking.getTripId());
                startActivity(intent);
            }
        });
    }
    public void loadTripbyAccountType(){
        DatabaseReference reference= FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(userId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null) {
                    loadTrip(userPro.getAccountType());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void checkAcountTyepeAddBookingToArray(User.AccountType acountType,Booking book){
        if(acountType.equals(User.AccountType.DRIVER_ACCOUNT)){
            if(book.getDriverId().equals(userId)) {
                bookings.add(book);
            }
        }
        else{
            if(book.getPassagerId().equals(userId)) {
                bookings.add(book);
            }
        }
    }

}