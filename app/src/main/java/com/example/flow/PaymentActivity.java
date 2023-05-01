
package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flow.entities.Booking;
import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;

public class PaymentActivity extends AppCompatActivity {
    private String tripId, driverName, to, from,driverId,passagerId,tripDateTime,tripPrice,userId;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;
    private String apiKey;
    private Button paymenyButton;
    private TextView paymentCon;
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
        tripId = intent.getStringExtra("TripId");
        paymenyButton = findViewById(R.id.paymentButton);
        paymentCon = findViewById(R.id.paymentConfirmationText);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarPayment);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
        apiKey = "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";
    }
    public void payTrip(View view){
        String bookingId = FirebaseDatabase.getInstance().getReference("Booking").push().getKey();
        Booking booking = new Booking(passagerId,driverId,from,to,bookingId,tripDateTime,driverName,tripPrice);
        booking.setTripId(tripId);
        FirebaseDatabase.getInstance().getReference("Booking")
                .child(bookingId)
                .setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            String apiKey = "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";
                            String fromEmail = "desirekeka11@gmail.com";
                            String toEmail = "desirekeka11@gmail.com";
                            String subject = "Booking Conformation";
                            String message = "Your booking confirmation";

                            SendGridEmailSender sender = new SendGridEmailSender(apiKey, fromEmail, toEmail, subject, message);
                            sender.execute();
                            bookTrip();
                            Toast.makeText(PaymentActivity.this, "Booking Complited", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            System.out.println("nini lisusu");
                        }
                    }
                });
    }

    public void bookTrip(){
        HashMap map = new HashMap();
        map.put("tripBook",true);
        FirebaseDatabase.getInstance().getReference("Trips")
                .child(tripId)
                .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                        else{
                            System.out.println("nini lisusu");
                        }
                    }
                });
    }
    public class SendGridEmailSender extends AsyncTask<Void, Void, Void> {
        String API_URL = "https://api.sendgrid.com/v3/mail/send";
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        private String apiKey;
        private String fromEmail;
        private String toEmail;
        private String subject;
        private String message;

        public SendGridEmailSender(String apiKey, String fromEmail, String toEmail, String subject, String message) {
            this.apiKey = apiKey;
            this.fromEmail = fromEmail;
            this.toEmail = toEmail;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            try {
                String json = buildJson(fromEmail, toEmail, subject, message);
                RequestBody requestBody = RequestBody.create(json, JSON);

                Request request = new Request.Builder()
                        .url(API_URL)
                        .post(requestBody)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                Log.d("SendGridEmailSender", "Email sent successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            paymenyButton.setVisibility(View.INVISIBLE);
            paymentCon.setVisibility(View.VISIBLE);
        }

        private String buildJson(String fromEmail, String toEmail, String subject, String message) {
            return "{\"personalizations\":[{\"to\":[{\"email\":\"" + toEmail + "\"}]}],\"from\":{\"email\":\"" + fromEmail + "\"},\"subject\":\"" + subject + "\",\"content\":[{\"type\":\"text/plain\",\"value\":\"" + message + "\"}]}";
        }
    }
}