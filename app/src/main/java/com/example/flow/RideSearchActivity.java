package com.example.flow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RideSearchActivity extends AppCompatActivity {
    EditText form, to;
    Button button;
    String selectedDate,userId;
    DatePickerDialog datePickerDialog;
    BottomNavigationView bottomNavigationView;
    MenuSetter menuSetter;
    
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
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
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
}