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

public class BookedTripActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private String userId;
    private ListView listView;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_trip);

        bottomNavigationView = findViewById(R.id.nabBarBookedTrip);
        reference = FirebaseDatabase.getInstance().getReference("Booking");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        listView = findViewById(R.id.BookedTripList);
        setMenu();

        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void loadTrip(User.AccountType acountType ){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Booking> tripItems = new ArrayList<Booking>();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Booking book= ds.getValue(Booking.class);
                    if(acountType.equals(User.AccountType.DRIVER_ACCOUNT)){
                        if(book.getDriverId().equals(userId)) {
                            tripItems.add(new Booking(book.getPassagerId(), book.getDriverId(), book.getFrom(), book.getTo(), book.getBookingId(), book.getDateTime(), book.getDriverName(), book.getPrice()));
                        }
                    }
                    else{
                        if(book.getPassagerId().equals(userId)) {
                            tripItems.add(new Booking(book.getPassagerId(), book.getDriverId(), book.getFrom(), book.getTo(), book.getBookingId(), book.getDateTime(), book.getDriverName(), book.getPrice()));
                        }
                    }
                    BookingAdaptor adapter = new BookingAdaptor (getApplicationContext(),R.layout.book_item,tripItems);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Booking booking = tripItems.get(i);
                            Intent intent = new Intent(getApplicationContext(),BookedTripViewActivity.class);
                            intent.putExtra("from",booking.getFrom());
                            intent.putExtra("to",booking.getTo());
                            intent.putExtra("DriverName",booking.getDriverName());
                            intent.putExtra("DriverId",booking.getDriverId());
                            intent.putExtra("TripDateTime",booking.getDateTime());
                            intent.putExtra("TripPrice",booking.getPrice());
                            intent.putExtra("BookingId",booking.getBookingId());
                            startActivity(intent);
                        }
                    });
                }
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
                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
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