package com.example.flow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.flow.entities.Conversation;
import com.example.flow.entities.MessageRead;
import com.example.flow.entities.Trip;
import com.example.flow.entities.User;
import com.example.flow.listAdapers.MmsAdaptor;
import com.example.flow.utilities.MenuSetter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private String userId;
    private String partTtwoId = "bp5wXcRt2ubseOzU0nVxuPUOuI02";
    private MenuSetter menuSetter;
    private EditText text;
    private MessageRead message;
    private ListView listView;
    private MmsAdaptor mmsAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = findViewById(R.id.TexterList);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bottomNavigationView = findViewById(R.id.nabBarChat);
        menuSetter= new MenuSetter(bottomNavigationView,this,userId);
        menuSetter.setMenu();
        text = findViewById(R.id.messageetext);

        Intent intent = getIntent();
        partTtwoId = intent.getStringExtra("otherParty");
        loadMesages();

    }
    public void sendMessage(View view){
        message = new MessageRead(userId,text.getText().toString());
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    saveMessage(addNewMessage(userPro.getConversations(),userId,partTtwoId));
                    loadPartyTwoConversation();
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
    public void saveMessage(ArrayList<Conversation> conversations){
        FirebaseDatabase.getInstance().getReference("Users")
                .child(userId)
                .child("conversations")
                .setValue(conversations);
    }
    public void saveMessageForPartyTwo(ArrayList<Conversation> conversations){
        FirebaseDatabase.getInstance().getReference("Users")
                .child(partTtwoId)
                .child("conversations")
                .setValue(conversations);
        text.setText("");
    }
    public void loadPartyTwoConversation(){
        FirebaseDatabase.getInstance().getReference("Users").child(partTtwoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                if(userPro !=null){
                    saveMessageForPartyTwo(addNewMessage(userPro.getConversations(),partTtwoId,userId));
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
    public ArrayList<Conversation> addNewMessage(ArrayList<Conversation> conversations,String partOne,String partTtwo){
        Boolean found = false;
        for(int i =0;i < conversations.size();i++){
            if (conversations.get(i).getPartyOneId().equals(partOne) && conversations.get(i).getPartTwoId().equals(partTtwo)){
                conversations.get(i).addMessage(message);
                found = true;
            }
        }
        if(found){
            return conversations;
        }
        Conversation conversation = new Conversation(partOne,partTtwo);
        conversation.addMessage(message);
        conversations.add(conversation);
        return conversations;
    }
    public void loadMesages(){
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                ArrayList<Conversation> conversations = userPro.getConversations();
                if(userPro !=null){
                    for(int i = 0; i < conversations.size(); i++){
                        if(conversations.get(i).getPartTwoId().equals(partTtwoId) && conversations.get(i).getPartyOneId().equals(userId)){
                            System.out.println("koko");
                            listView.setAdapter(new MmsAdaptor(getApplicationContext(),R.layout.mms_item,conversations.get(i).getMessages(),userId));
                        }
                        else{
                            System.out.println("ko");
                        }
                    }
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
}