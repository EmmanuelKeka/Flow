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

public class ProfilItemAdaper extends ArrayAdapter<ProfilItem> {
    private Context mycontext;
    private int myresource;
    public ProfilItemAdaper(@NonNull Context context, int resource, @NonNull ArrayList<ProfilItem> objects) {
        super(context, resource, objects);
        mycontext = context;
        myresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        convertView = inflater.inflate(myresource,parent,false);
        ImageView image = convertView.findViewById(R.id.profilOptionImage);
        TextView text = convertView.findViewById(R.id.profilOptionText);
        text.setText(getItem(position).getItemText());
        image.setImageBitmap(getItem(position).getItemImage());
        return convertView;
    }
}
