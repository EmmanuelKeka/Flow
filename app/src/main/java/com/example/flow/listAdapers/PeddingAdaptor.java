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
import com.example.flow.entities.PendingRate;
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

public class PeddingAdaptor extends ArrayAdapter<PendingRate> {
    private Context mycontext;
    private int myresource;
    private String userId;
    public PeddingAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<PendingRate> objects) {
        super(context, resource, objects);
        mycontext = context;
        myresource = resource;
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        convertView = inflater.inflate(myresource,parent,false);
        TextView texterName = convertView.findViewById(R.id.PeddingTextername);
        ImageView image = convertView.findViewById(R.id.PeddingTexterImage);
        String id = "";
        if(userId.equals(getItem(position).getPassengerId())){
            id = getItem(position).getDriverId();
        }
        else {
            id = getItem(position).getPassengerId();
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + id);
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
                .child(id)
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
