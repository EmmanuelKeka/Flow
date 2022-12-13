package com.example.flow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        drivername.setText("Driver name: " + getItem(position).getDriverName());
        from.setText("From: "+ getItem(position).getFrom());
        to.setText("To: "+ getItem(position).getTo());
        return convertView;
    }
}