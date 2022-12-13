package com.example.flow;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class tripAdapter extends ArrayAdapter<TripItem> {
    private Context mycontext;
    private int myresource;
    public tripAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TripItem> objects) {
        super(context, resource, objects);
        mycontext = context;
        myresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mycontext);
        convertView = inflater.inflate(myresource,parent,false);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + getItem(position).getDriverId());
        TextView from = convertView.findViewById(R.id.fromTextFrag);
        TextView to = convertView.findViewById(R.id.toTextFrag);
        TextView dateTime = convertView.findViewById(R.id.DateTimeTextFrag);
        TextView driverNameFrag = convertView.findViewById(R.id.driverNameFrag);
        TextView priceFrag = convertView.findViewById(R.id.priceFrag);
        ImageView image = convertView.findViewById(R.id.profilOptionDriverImage);
        from.setText("from: "+ getItem(position).getForm());
        to.setText("To: " + getItem(position).getTo());
        dateTime.setText("Date & time: " + getItem(position).getTripDateTime());
        priceFrag.setText("Price: "+ getItem(position).getTripPrice());
        driverNameFrag.setText("Driver name: " + getItem(position).getDriverName());
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
        return convertView;
    }
}
