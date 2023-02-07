package com.example.flow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.flow.entities.Trip;
import com.example.flow.entities.User;
import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CreatTripActivity extends AppCompatActivity {
    private EditText from,to,price;
    private DatabaseReference reference;
    private String userId;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button Datebutton,TimeButton;
    private String selectedDate = "";
    private String selectedTime = "";
    private BottomNavigationView bottomNavigationView;
    MenuSetter menuSetter;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_trip);

        from = findViewById(R.id.creatTripFrom);
        to = findViewById(R.id.creatTripTo);
        Datebutton = findViewById(R.id.selectdateInCreatTrip);
        TimeButton = findViewById(R.id.selectTimeInCreatTrip);
        price = findViewById(R.id.creatPrice);


        Calendar calendar = Calendar.getInstance();
        int hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int mns = calendar.get(Calendar.MINUTE);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),hrs,mns);
        Datebutton.setText(android.text.format.DateFormat.format("dd/MM/yyyy",calendar));
        setDatelisrener();
        datePickerDialog.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        System.out.println(hrs);
        TimeButton.setText(android.text.format.DateFormat.format("HH:mm aa",calendar));
        setTimeListen();
        timePickerDialog.updateTime(hrs,mns);

        reference = FirebaseDatabase.getInstance().getReference("Trips");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarCreatTrip);

        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();

    }
    public void SaveTrip(View view){
        if(!validateInput()){
            return;
        }
        String fromText = from.getText().toString();
        String toText = to.getText().toString();
        String priceText = price.getText().toString();
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    String tripId = FirebaseDatabase.getInstance().getReference("Trips").push().getKey();
                    saveTrip(new Trip(fromText, toText, userPro.getUsername(), userId , selectedDate +" "+selectedTime,priceText,tripId),tripId);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void saveTrip(Trip trip, String tripId){
        FirebaseDatabase.getInstance().getReference("Trips")
                .child(tripId)
                .setValue(trip).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            from.setText("");
                            to.setText("");
                            price.setText("");
                        }
                    }
                });
    }

    public void selectdateCreatTrip(View view){
        datePickerDialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDatelisrener(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day,0,0);
                selectedDate = android.text.format.DateFormat.format("dd/MM/yyyy",calendar).toString();
                Datebutton.setText(selectedDate);
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

    public void setTimeListen(){
        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(0,0,0,hour,minute);
                String timeSec = android.text.format.DateFormat.format("HH:mm aa",calendar).toString();
                TimeButton.setText(timeSec);
                selectedTime = timeSec;
                timePickerDialog.updateTime(hour,minute);
            }
        };
        timePickerDialog = new TimePickerDialog(this,style,timeSetListener,12,0,false);
    }

    public void selectTimeCreatTrip(View view){
        timePickerDialog.show();
    }

    public boolean validateInput(){
        System.out.println(selectedDate);
        if(from.getText().toString().isEmpty()){
            from.setError("enter starting Location");
            from.requestFocus();
            return false;
        }
        if(to.getText().toString().isEmpty()){
            to.setError("enter ending Location");
            to.requestFocus();
            return false;
        }
        if(price.getText().toString().isEmpty()){
            price.setError("enter price");
            price.requestFocus();
            return false;
        }
        if(selectedDate.equals("")){
            Toast.makeText(this, "please select a date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedTime.equals("")){
            Toast.makeText(this, "please select a time", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}