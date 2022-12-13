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

public class SearchResultActivity extends AppCompatActivity {
    ListView listView;
    String date,from,to,userId;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;
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
        setMenu();

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
                ArrayList<TripItem> tripItems = new ArrayList<TripItem>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Trip trip = ds.getValue(Trip.class);
                    if(trip !=null) {
                        String tripDate = trip.getDateTime().toString().split(" ")[0];
                        System.out.println(tripDate);
                        if(trip.getFrom().equals(from) && trip.getTo().equals(to)&& tripDate.equals(date)) {
                            tripItems.add(new TripItem(trip.getFrom(), trip.getTo(), "imageLink", trip.getDriverName(), trip.getDriverId(), trip.getDateTime(), trip.getPrice()));
                        }
                    }

                }
                tripAdapter adapter = new tripAdapter(SearchResultActivity.this, R.layout.ride_item, tripItems);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TripItem tripItem = tripItems.get(i);
                        Intent intent = new Intent(SearchResultActivity.this, RideViewActivity.class);
                        intent.putExtra("from", tripItem.getForm());
                        intent.putExtra("to", tripItem.getTo());
                        intent.putExtra("DriverName", tripItem.getDriverName());
                        intent.putExtra("ImageName", tripItem.getImageName());
                        intent.putExtra("DriverId", tripItem.getDriverId());
                        intent.putExtra("TripDateTime", tripItem.getTripDateTime());
                        intent.putExtra("TripPrice", tripItem.getTripPrice());
                        intent.putExtra("PassagerId", userId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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