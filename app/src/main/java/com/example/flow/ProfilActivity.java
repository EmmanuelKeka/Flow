package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.flow.entities.Rate;
import com.example.flow.listAdapers.ProfilItem;
import com.example.flow.listAdapers.ProfilItemAdaper;
import com.example.flow.entities.User;
import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {
    ListView listView;
    TextView username,accountType,driverRate,passengerRate;
    String userId;
    BottomNavigationView bottomNavigationView;
    ImageView profil;
    StorageReference storageReference;
    MenuSetter menuSetter;
    ArrayList<ProfilItem> profilItems = new ArrayList<ProfilItem>();
    DecimalFormat df = new DecimalFormat("0.00");
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
        driverRate = findViewById(R.id.driverRateing);
        passengerRate = findViewById(R.id.passengerRating);

        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    double driverRates = meanCal(userPro.getDriverRating());
                    double passengerRates = meanCal(userPro.getPassengerRating());
                    setProfilItems(userPro.getUsername(), userPro.getAccountType().toString(),driverRates,passengerRates);
                    setAdapeterAndList(profilItems,userPro.getAccountType().toString());
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
    public void setProfilItems(String usernames, String accountTypes,double driverRating,double passengerRating){
        username.setText(usernames);
        if(accountTypes.equals("DRIVER_ACCOUNT")){
            accountType.setText(("DRIVER ACCOUNT"));
        }
        else{
            accountType.setText(("PASSENGER ACCOUNT"));
        }
        driverRate.setText("Driver Rating: " +  df.format(driverRating));
        passengerRate.setText("Passenger Rating: " +  df.format(passengerRating));
        profilItems.add(new ProfilItem(R.drawable.ic_action_name_change, "Change Username"));
        profilItems.add(new ProfilItem(R.drawable.ic_action_account_change, "Change Acount Type"));
        profilItems.add(new ProfilItem(R.drawable.ic_action_booking, "Trips Booked"));
        profilItems.add(new ProfilItem(R.drawable.ic_action_image_change,"Set Profile Image"));
        profilItems.add(new ProfilItem(R.drawable.ic_action_list_pending, "Pedding review"));

        if(accountTypes.equals(User.AccountType.DRIVER_ACCOUNT.toString()))
            profilItems.add(new ProfilItem(R.drawable.ic_action_trip_post, "Trips posted"));

        profilItems.add(new ProfilItem(R.drawable.ic_action_logout, "Log Out"));
    }
    public void setAdapeterAndList(ArrayList<ProfilItem> profilItems , String accountTypes){
        ProfilItemAdaper adaper = new ProfilItemAdaper(getApplicationContext(),R.layout.profil_item,profilItems);
        listView.setAdapter(adaper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setListener(i,accountTypes);
            }
        });
    }
    public void setListener(int i,String accountTypes){
        switch(i){
            case 0:
                FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userPro = snapshot.getValue(User.class);
                        if(userPro !=null){
                            Intent intent = new Intent(getApplicationContext(), ChangeUsernameActivity.class);
                            intent.putExtra("username",userPro.getUsername());
                            startActivity(intent);
                        }
                        else{
                            System.out.println("nini papa" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case 1:
                Intent intent = new Intent(getApplicationContext(),ChangeAccountTypeActivity.class);
                intent.putExtra("accountType",accountTypes);
                startActivity(intent);
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(),BookedTripActivity.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(),SelectProfilImageActivity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(),PendingRateMainActivity.class));
                break;
            case 5:
                if(accountTypes.equals(User.AccountType.DRIVER_ACCOUNT.toString())) {
                    startActivity(new Intent(getApplicationContext(), TripPostedActivity.class));
                    return;
                }

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case 6:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
    public double meanCal(ArrayList<Rate> rates){
        System.out.println("size === " + rates.size());
        double mean = 0.0;
        if(rates.size() <= 0){
            return mean;
        }
        for(int i =0;i< rates.size(); i++){
            mean = mean +  rates.get(i).getStartNumber();
        }
        return mean / rates.size();
    }
}