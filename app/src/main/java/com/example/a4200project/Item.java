package com.example.a4200project;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Item {
    int id;
    String itemName;
    String purchasedFrom;
    String purchaseDate;
    double purchasePrice;
    String storageLocation;
    boolean sold;
    String platformSoldOn;
    String saleDate;
    double soldPrice;
    double fees;

    public Item(String string){
    }
    public Item(String itemName, String purchasedFrom, String purchaseDate, double purchasePrice, String storageLocation){
        this.itemName = itemName;
        this.purchasedFrom = purchasedFrom;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.storageLocation = storageLocation;
        this.sold = false;
        this.saleDate = "null";
        this.platformSoldOn = "null";
        this.soldPrice = 0;
        this.fees = 0;
    }
    public Item(int id, String itemName, String purchasedFrom, String purchaseDate, double purchasePrice, String storageLocation){
        this.id = id;
        this.itemName = itemName;
        this.purchasedFrom = purchasedFrom;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.storageLocation = storageLocation;
        this.sold = false;
        this.saleDate = "null";
        this.platformSoldOn = "null";
        this.soldPrice = 0;
        this.fees = 0;
    }
    public Item(String itemName, String purchasedFrom, String purchaseDate, double purchasePrice, String storageLocation, boolean sold, String soldDate, String platformSoldOn, double soldPrice, double fees){
        this.itemName = itemName;
        this.purchasedFrom = purchasedFrom;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.storageLocation = storageLocation;
        this.sold = sold;
        this.saleDate = soldDate;
        this.platformSoldOn = platformSoldOn;
        this.soldPrice = soldPrice;
        this.fees = fees;
    }
    public Item(int id, String itemName, String purchasedFrom, String purchaseDate, double purchasePrice, String storageLocation, boolean sold, String soldDate, String platformSoldOn, double soldPrice, double fees){
        this.id = id;
        this.itemName = itemName;
        this.purchasedFrom = purchasedFrom;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.storageLocation = storageLocation;
        this.sold = sold;
        this.saleDate = soldDate;
        this.platformSoldOn = platformSoldOn;
        this.soldPrice = soldPrice;
        this.fees = fees;
    }
    public int getId(){ return id;}
    public String getItemName() {
        return itemName;
    }
    public String getPurchaseDate(){
        return purchaseDate;
    }

    public String getPlatformSoldOn() {
        return platformSoldOn;
    }

    public double getSoldPrice() {
        return soldPrice;
    }
    public double getSaleFees(){return fees;}

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getPurchasedFrom() {
        return purchasedFrom;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public String getSaleDate(){return saleDate;}
    public boolean getSold(){return sold;}
}
