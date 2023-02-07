package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookedTripViewActivity extends AppCompatActivity {
    private TextView driverName,dateTime,from,to,price;
    private DatabaseReference reference;
    private String bookingIdText,userId;
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

        driverName.setText("Driver Name: " + driverNameText);
        dateTime.setText("Date & Time: "+ dateTimeText);
        from.setText("From: " + fromText);
        to.setText("To: "+ toText);
        price.setText("Price: "+ priceText);


    }
    public void cancelBookedTrip(View view){
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
}