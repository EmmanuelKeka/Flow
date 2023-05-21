package com.example.flow.listAdapers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flow.R;
import com.example.flow.entities.Booking;
import com.example.flow.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingAdaptor extends ArrayAdapter<Booking> {
    private Context mycontext;
    private int myresource;
    public BookingAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Booking> objects) {
        super(context, resource, objects);
        mycontext = context;
        myresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        convertView = inflater.inflate(myresource,parent,false);
        TextView drivername = convertView.findViewById(R.id.bookItemDriverName);
        TextView from = convertView.findViewById(R.id.bookItemFrom);
        TextView to = convertView.findViewById(R.id.bookItemTo);
        if(getItem(position).getDriverId().equals(FirebaseAuth.getInstance().getUid())){
            FirebaseDatabase.getInstance().getReference("Users").child(getItem(position).getPassagerId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userPro = snapshot.getValue(User.class);
                    if(userPro !=null){
                        drivername.setText("Passenger Name: " + userPro.getUsername());
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
        else {
            drivername.setText("Driver name: " + getItem(position).getDriverName());
        }

        drivername.setText("Driver name: " + getItem(position).getDriverName());
        from.setText("From: "+ getItem(position).getFrom());
        to.setText("To: "+ getItem(position).getTo());
        return convertView;
    }
}