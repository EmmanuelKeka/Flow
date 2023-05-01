package com.example.flow;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.flow.entities.Conversation;
import com.example.flow.entities.PendingRate;
import com.example.flow.entities.User;
import com.example.flow.listAdapers.ChatListAdaptor;
import com.example.flow.listAdapers.PeddingAdaptor;
import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingRateMainActivity extends AppCompatActivity {
    PeddingAdaptor peddingAdapotor = null;
    ListView peddingList;
    String userId;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_rate_main);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        peddingList = findViewById(R.id.PeddingList);
        bottomNavigationView = findViewById(R.id.nabBarPeddingRate);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
        populatList();
    }
    public void populatList() {
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PendingRate> pendingRates = dataSnapshot.getValue(User.class).getPendingRates();
                peddingAdapotor = new PeddingAdaptor(getApplicationContext(),R.layout.pending_item,pendingRates);
                peddingList.setAdapter(peddingAdapotor);
                peddingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        PendingRate pendingRate = pendingRates.get(i);
                        Intent intent = new Intent(getApplicationContext(),RatingActivity.class);
                        intent.putExtra("driverId",pendingRate.getDriverId());
                        intent.putExtra("Index",""+i);
                        intent.putExtra("PassengerId",pendingRate.getPassengerId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}