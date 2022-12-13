package com.example.flow;

import android.graphics.Bitmap;

public class ProfilItem {
    private Bitmap itemImage;
    private String itemText;

    public ProfilItem(Bitmap itemImage, String itemText) {
        this.itemImage = itemImage;
        this.itemText = itemText;
    }

    public Bitmap getItemImage() {
        return itemImage;
    }

    public String getItemText() {
        return itemText;
    }
}
