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

public class TripPostViewActivity extends AppCompatActivity {
    TextView driverName,dateTime,from,to,price;
    DatabaseReference reference;
    String tripId, userId;
    BottomNavigationView bottomNavigationView;
    MenuSetter menuSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_post_view);

        driverName = findViewById(R.id.PostViewDriverName);
        dateTime = findViewById(R.id.PostViewDateTime);
        from = findViewById(R.id.PostViewFrom);
        to = findViewById(R.id.PostViewTo);
        price = findViewById(R.id.PostViewPrice);
        reference = FirebaseDatabase.getInstance().getReference("Trips");
        userId = FirebaseAuth.getInstance().getUid();
        bottomNavigationView = findViewById(R.id.nabBarTipPostView);

        Intent intent = getIntent();
        String driverNameText = intent.getStringExtra("DriverName");
        String dateTimeText = intent.getStringExtra("TripDateTime");
        String fromText = intent.getStringExtra("from");
        String toText = intent.getStringExtra("to");
        String priceText = intent.getStringExtra("TripPrice");
        tripId = intent.getStringExtra("TripId");
        System.out.println(tripId);

        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

        driverName.setText("Driver Name: " + driverNameText);
        dateTime.setText("Date & Time: "+ dateTimeText);
        from.setText("From: " + fromText);
        to.setText("To: "+ toText);
        price.setText("Price: "+ priceText);
    }
    public void deleteTripPost(View view){
        reference.child(tripId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(),TripPostedActivity.class);
                        startActivity(intent);
                    }
                });
    }
}