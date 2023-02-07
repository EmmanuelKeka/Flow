package com.example.flow.listAdapers;

public class ProfilItem {
    private int itemImage;
    private String itemText;

    public ProfilItem(int itemImage, String itemText) {
        this.itemImage = itemImage;
        this.itemText = itemText;
    }

    public int getItemImage() {
        return itemImage;
    }

    public String getItemText() {
        return itemText;
    }
}
