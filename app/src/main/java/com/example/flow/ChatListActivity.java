package com.example.flow;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.flow.entities.Booking;
import com.example.flow.entities.Conversation;
import com.example.flow.entities.User;
import com.example.flow.listAdapers.ChatListAdaptor;
import com.example.flow.utilities.MenuSetter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {
    ChatListAdaptor chatListAdaptor = null;
    ListView chatList;
    String userId;
    private BottomNavigationView bottomNavigationView;
    private MenuSetter menuSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        chatList = findViewById(R.id.TexterList);
        bottomNavigationView = findViewById(R.id.nabBarChatList);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
        populatList();
    }
    public void populatList() {
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Conversation> conversations = dataSnapshot.getValue(User.class).getConversations();
                chatListAdaptor = new ChatListAdaptor(getApplicationContext(),R.layout.message_item,conversations);
                chatList.setAdapter(chatListAdaptor);
                chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Conversation conversation = conversations.get(i);
                        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                        intent.putExtra("otherParty",conversation.getPartTwoId());
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