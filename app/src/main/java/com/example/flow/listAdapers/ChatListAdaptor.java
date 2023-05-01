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
import com.example.flow.entities.Trip;
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

public class ChatListAdaptor extends ArrayAdapter<Conversation> {
    private Context mycontext;
    private int myresource;
    public ChatListAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Conversation> objects) {
        super(context, resource, objects);
        mycontext = context;
        myresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        convertView = inflater.inflate(myresource,parent,false);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + getItem(position).getPartTwoId());
        TextView texterName = convertView.findViewById(R.id.Textername);
        ImageView image = convertView.findViewById(R.id.TexterImage);
        try{
            File imageFile = File.createTempFile("tempImage","jpg");
            storageReference.getFile(imageFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                            image.setImageBitmap(bitmap);
                        }
                    });
        }
        catch (Exception e){

        }
        FirebaseDatabase.getInstance().getReference("Users")
                .child(getItem(position).getPartTwoId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userPro = snapshot.getValue(User.class);
                    if(userPro !=null){
                        texterName.setText(userPro.getUsername());
                    }
                    else{
                        System.out.println("nini papa" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        return convertView;
    }
}
