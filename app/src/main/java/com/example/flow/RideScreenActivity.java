package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_screen);

        reference = FirebaseDatabase.getInstance().getReference("Trips");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarRideScreen);
        setMenu();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listView = findViewById(R.id.ListTripRideList);
                ArrayList<TripItem> tripItems = new ArrayList<TripItem>();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Trip trip= ds.getValue(Trip.class);
                    tripItems.add(new TripItem(trip.getFrom(),trip.getTo(),"imageLink",trip.getDriverName(),trip.getDriverId(),trip.getDateTime(),trip.getPrice()));

                }
                tripAdapter adapter = new tripAdapter(RideScreenActivity.this,R.layout.ride_item,tripItems);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TripItem tripItem = tripItems.get(i);
                        Intent intent = new Intent(RideScreenActivity.this,RideViewActivity.class);
                        intent.putExtra("from",tripItem.getForm());
                        intent.putExtra("to",tripItem.getTo());
                        intent.putExtra("DriverName",tripItem.getDriverName());
                        intent.putExtra("ImageName",tripItem.getImageName());
                        intent.putExtra("DriverId",tripItem.getDriverId());
                        intent.putExtra("TripDateTime",tripItem.getTripDateTime());
                        intent.putExtra("TripPrice",tripItem.getTripPrice());
                        intent.putExtra("PassagerId" , userId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void GoToSearchFromTrips(View view){
        Intent intent = new Intent(this,RideSearchActivity.class);
    }
    public void setMenu(){
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    if(userPro.getAccountType().equals(User.AccountType.DRIVER_ACCOUNT)) {
                        bottomNavigationView.inflateMenu(R.menu.nav_button_driver);
                        setMenuListenForDriver();
                    }
                    else{
                        bottomNavigationView.inflateMenu(R.menu.nav_bar_passager);
                        setMenuListenForPassager();
                    }
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setMenuListenForDriver(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeNav:
                        startActivity(new Intent(getApplicationContext(),ChatActivity.class));
                        break;
                    case R.id.profileNav:
                        startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
                        break;
                    case R.id.tripNav:
                        startActivity(new Intent(getApplicationContext(),CreatTripActivity.class));
                        break;
                }
                return true;
            }
        });
    }
    public void setMenuListenForPassager(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeNav:
                        startActivity(new Intent(getApplicationContext(),ChatActivity.class));
                        break;
                    case R.id.profileNav:
                        startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
                        break;
                    case R.id.searchNav:
                        startActivity(new Intent(getApplicationContext(),RideScreenActivity.class));
                        break;
                }
                return true;
            }
        });
    }
}