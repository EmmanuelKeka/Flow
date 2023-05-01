package com.example.flow.listAdapers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flow.R;
import com.example.flow.entities.Conversation;
import com.example.flow.entities.MessageRead;
import com.example.flow.entities.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class MmsAdaptor extends ArrayAdapter<MessageRead> {
    private Context mycontext;
    private String userId;
    private int myresource;
    public MmsAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<MessageRead> objects,String userId) {
        super(context, resource, objects);
        mycontext = context;
        myresource = resource;
        this.userId = userId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        convertView = inflater.inflate(myresource,parent,false);
        TextView sender = convertView.findViewById(R.id.SenderM);
        TextView resever = convertView.findViewById(R.id.ReseverM);
        if(!userId.equals(getItem(position).getSenderID())){
            resever.setText(getItem(position).getMessage());
            sender.setVisibility(View.GONE);
        }
        else{
            sender.setText(getItem(position).getMessage());
            resever.setVisibility(View.GONE);
        }
        return convertView;
    }
}
