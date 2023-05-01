package com.example.flow.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.flow.BookedTripActivity;
import com.example.flow.BookedTripViewActivity;
import com.example.flow.ChangeAccountTypeActivity;
import com.example.flow.ChatActivity;
import com.example.flow.ChatListActivity;
import com.example.flow.CreatTripActivity;
import com.example.flow.DriverProfilActivity;
import com.example.flow.PaymentActivity;
import com.example.flow.ProfilActivity;
import com.example.flow.R;
import com.example.flow.RatingActivity;
import com.example.flow.RideScreenActivity;
import com.example.flow.RideSearchActivity;
import com.example.flow.RideViewActivity;
import com.example.flow.SearchResultActivity;
import com.example.flow.SelectProfilImageActivity;
import com.example.flow.TripPostViewActivity;
import com.example.flow.TripPostedActivity;
import com.example.flow.entities.User;
import com.example.flow.listAdapers.PeddingAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuSetter {
    private BottomNavigationView bottomNavigationView;
    private Context context;
    private String userId;

    public MenuSetter(BottomNavigationView bottomNavigationView, Context context, String userId) {
        this.bottomNavigationView = bottomNavigationView;
        this.context = context;
        this.userId = userId;
    }

    public void setMenu(){
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userPro = snapshot.getValue(User.class);
                setMenuByAccountType(userPro);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void setMenuByAccountType(User userPro){
        if(userPro !=null){
            if(userPro.getAccountType().equals(User.AccountType.DRIVER_ACCOUNT)) {
                bottomNavigationView.inflateMenu(R.menu.nav_button_driver);
                setMenuListenForDriver();
            }
            else{
                bottomNavigationView.inflateMenu(R.menu.nav_bar_passager);
                setMenuListenForPassager();
            }
            setIcon();
        }
    }
    public void setMenuListenForDriver(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigateToSelectedPage(item);
                return true;
            }
        });
    }
    public void setMenuListenForPassager(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigateToSelectedPage(item);
                return true;
            }
        });
    }
    public void navigateToSelectedPage(MenuItem item){
        switch (item.getItemId()){
            case R.id.homeNav:
                context.startActivity(new Intent(context, ChatListActivity.class));
                break;
            case R.id.profileNav:
                context.startActivity(new Intent(context, ProfilActivity.class));
                break;
            case R.id.searchNav:
                context.startActivity(new Intent(context, RideScreenActivity.class));
                break;
            case R.id.tripNav:
                context.startActivity(new Intent(context, CreatTripActivity.class));
                break;
        }
    }
    public void setIcon(){
        if(context.getClass().getName().equals(ChatActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
        else if(context.getClass().getName().equals(ChatListActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
        else if(context.getClass().getName().equals(BookedTripActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(BookedTripViewActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(ChangeAccountTypeActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(CreatTripActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        else if(context.getClass().getName().equals(DriverProfilActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        else if(context.getClass().getName().equals(PaymentActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(ProfilActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(PeddingAdaptor.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(RatingActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(RideScreenActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        else if(context.getClass().getName().equals(RideSearchActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        else if(context.getClass().getName().equals(RideViewActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        else if(context.getClass().getName().equals(SearchResultActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        else if(context.getClass().getName().equals(SelectProfilImageActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if(context.getClass().getName().equals(TripPostedActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        else if(context.getClass().getName().equals(TripPostViewActivity.class.getName())){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }

    }
}
