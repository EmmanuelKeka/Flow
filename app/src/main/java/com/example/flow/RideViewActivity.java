package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class RideViewActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private String imageName, driverName, to, from,driverId,passagerId,tripDateTime,tripPrice,userId;
    private TextView fromView,toView,driverNameView,tripDateTimeView,tripPriceView;
    private GoogleMap map;
    private BottomNavigationView bottomNavigationView;
    private Marker marker;
    private ImageView imageView;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_view);

        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.the_map);
        mf.getMapAsync(this);

        Intent intent = getIntent();

        imageName = intent.getStringExtra("ImageName");
        driverName = intent.getStringExtra("DriverName");
        to =intent.getStringExtra("to");
        from = intent.getStringExtra("from");
        driverId = intent.getStringExtra("DriverId");
        passagerId = intent.getStringExtra("PassagerId");
        tripDateTime = intent.getStringExtra("TripDateTime");
        tripPrice = intent.getStringExtra("TripPrice");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarRideView);
        imageView = findViewById(R.id.profilImageRideView);
        storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + driverId);
        setMenu();
        setProfilImage();

        fromView = findViewById(R.id.fromViewTrop);
        toView = findViewById(R.id.toViewTrop);
        driverNameView = findViewById(R.id.driverNameViewTrop);
        tripDateTimeView = findViewById(R.id.TripDateTimeRideView);
        tripPriceView = findViewById(R.id.priceRideView);

        fromView.setText("From: " + from);
        driverNameView.setText("Driver Name: " + driverName);
        toView.setText("To: " + to);
        tripDateTimeView.setText("Date & Time: " + tripDateTime);
        tripPriceView.setText("Date & Time: " + tripPrice);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setOnMapLoadedCallback(this);
    }

    @Override
    public void onMapLoaded() {

    }

    public void bookTrip(View view){
        Intent intent = new Intent(this,PaymentActivity.class);
        intent.putExtra("from",from);
        intent.putExtra("to",to);
        intent.putExtra("DriverName",driverName);
        intent.putExtra("DriverId",driverId);
        intent.putExtra("TripDateTime",tripDateTime);
        intent.putExtra("TripPrice",tripPrice);
        intent.putExtra("PassagerId" , passagerId);
        startActivity(intent);
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
    public void setProfilImage(){
        try{
            File imageFile = File.createTempFile("tempImage","jpg");
            storageReference.getFile(imageFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                        }
                    });
        }
        catch (Exception e){

        }
    }
}