package com.example.flow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.appsearch.SearchResult;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RideSearchActivity extends AppCompatActivity {
    EditText form, to;
    Button button;
    String selectedDate,userId;
    DatePickerDialog datePickerDialog;
    BottomNavigationView bottomNavigationView;
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_search);

        form = findViewById(R.id.fromTextSearch);
        to = findViewById(R.id.toTextSearch);
        button = findViewById(R.id.selectdateInSearchButton);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0);
        button.setText(android.text.format.DateFormat.format("dd/MM/yyyy",calendar));
        setDatelisrener();
        datePickerDialog.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarRideSearch);
        setMenu();
        bottomNavigationView.setSelectedItemId(R.id.searchNav);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDatelisrener(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();

                calendar.set(year,month,day,0,0);
                selectedDate = android.text.format.DateFormat.format("dd/MM/yyyy",calendar).toString();
                button.setText(selectedDate);
                datePickerDialog.updateDate(year,month,day);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = Calendar.YEAR;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);


    }
    public void selectdateInSearch(View view){
        datePickerDialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    public void searchFortrip(View view){
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra("from",form.getText().toString());
        intent.putExtra("to",to.getText().toString());
        intent.putExtra("date",selectedDate);
        System.out.println(selectedDate);
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
}