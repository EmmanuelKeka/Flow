package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
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
import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {
    ListView listView;
    TextView username,accountType;
    String userId;
    BottomNavigationView bottomNavigationView;
    ImageView profil;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        listView = findViewById(R.id.profileListView);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        bottomNavigationView = findViewById(R.id.nabBarProfil);
        profil = findViewById(R.id.profilimageProfil);
        storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + userId);
        setProfilImage();

        username = findViewById(R.id.usernameProfil);
        accountType = findViewById(R.id.accountTypeProfil);
        setMenu();
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    username.setText("Username: " + userPro.getUsername());
                    accountType.setText("Account Type: " + userPro.getAccountType().toString());
                    ArrayList<ProfilItem> profilItems = new ArrayList<ProfilItem>();
                    profilItems.add(new ProfilItem(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),"Change Username"));
                    profilItems.add(new ProfilItem(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),"Change Acount Type"));
                    profilItems.add(new ProfilItem(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),"Trips Booked"));
                    profilItems.add(new ProfilItem(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),"Set Profile Image"));
                    profilItems.add(new ProfilItem(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),"Ratings"));

                    if(userPro.getAccountType().equals(User.AccountType.DRIVER_ACCOUNT))
                        profilItems.add(new ProfilItem(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),"Trips posted"));

                    profilItems.add(new ProfilItem(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background),"Log Out"));

                    ProfilItemAdaper adaper = new ProfilItemAdaper(getApplicationContext(),R.layout.profil_item,profilItems);
                    listView.setAdapter(adaper);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            System.out.println(i);
                            if(i == 0)
                                startActivity(new Intent(getApplicationContext(),ChangeUsername.class));
                            else if(i == 1){
                                Intent intent = new Intent(getApplicationContext(),ChangeAccountTypeActivity.class);
                                intent.putExtra("accountType",userPro.getAccountType().toString());
                                startActivity(intent);
                            }
                            else if(i == 2)
                                startActivity(new Intent(getApplicationContext(),BookedTripActivity.class));
                            else if(i == 3)
                                startActivity(new Intent(getApplicationContext(),SelectProfilImageActivity.class));
                            else if(i == 4)
                                startActivity(new Intent(getApplicationContext(),RatingActivity.class));
                            if(userPro.getAccountType().equals(User.AccountType.DRIVER_ACCOUNT)) {
                                if (i == 5) {
                                    startActivity(new Intent(getApplicationContext(), TripPostedActivity.class));
                                }
                                if (i == 6) {
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            }
                            else{
                                if (i == 5) {
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            }
                        }
                    });
                }
                else{
                    System.out.println("nini papa" + FirebaseAuth.getInstance().getCurrentUser().getUid());
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
    public void setProfilImage(){
        try{
            File imageFile = File.createTempFile("tempImage","jpg");
            storageReference.getFile(imageFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                            profil.setImageBitmap(bitmap);
                        }
                    });
        }
        catch (Exception e){

        }
    }
}