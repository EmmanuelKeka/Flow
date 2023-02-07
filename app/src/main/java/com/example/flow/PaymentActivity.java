
package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.flow.entities.Booking;
import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {
    private String imageName, driverName, to, from,driverId,passagerId,tripDateTime,tripPrice,userId;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        driverName = intent.getStringExtra("DriverName");
        to =intent.getStringExtra("to");
        from = intent.getStringExtra("from");
        driverId = intent.getStringExtra("DriverId");
        passagerId = intent.getStringExtra("PassagerId");
        tripDateTime = intent.getStringExtra("TripDateTime");
        tripPrice = intent.getStringExtra("TripPrice");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarPayment);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
    }
    public void payTrip(View view){
        String bookingId = FirebaseDatabase.getInstance().getReference("Booking").push().getKey();
        Booking booking = new Booking(passagerId,driverId,from,to,bookingId,tripDateTime,driverName,tripPrice);
        FirebaseDatabase.getInstance().getReference("Booking")
                .child(bookingId)
                .setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(PaymentActivity.this, "Booking Complited", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            System.out.println("nini lisusu");
                        }
                    }
                });
    }
}