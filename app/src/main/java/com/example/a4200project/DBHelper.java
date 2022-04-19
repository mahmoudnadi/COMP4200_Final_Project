package com.example.a4200project;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "rhDatabase";
    public static final String ITEMS_TABLE = "ITEMS_TABLE";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_PURCHASE_LOCATION = "PURCHASE_LOCATION";
    public static final String COLUMN_PURCHASE_PRICE = "PURCHASE_PRICE";
    public static final String COLUMN_PURCHASE_DATE = "PURCHASE_DATE";
    public static final String COLUMN_STORAGE_LOCATION = "STORAGE_LOCATION";
    public static final String COLUMN_SOLD = "SOLD";
    public static final String COLUMN_SOLD_PRICE = "SOLD_PRICE";
    public static final String COLUMN_SALE_DATE = "SALE_DATE";
    public static final String COLUMN_PLATFORM_SOLD_ON = "PLATFORM_SOLD_ON";
    public static final String COLUMN_SALE_FEES = "SALE_FEES";

    public static final Integer POSITION_ID = 0;
    public static final Integer POSITION_ITEM_NAME = 1;
    public static final Integer POSITION_PURCHASE_LOCATION = 2;
    public static final Integer POSITION_PURCHASE_PRICE = 3;
    public static final Integer POSITION_PURCHASE_DATE = 4;
    public static final Integer POSITION_STORAGE_LOCATION = 5;
    public static final Integer POSITION_SOLD = 6;
    public static final Integer POSITION_SALE_DATE = 7;
    public static final Integer POSITION_PLATFORM_SOLD_ON = 8;
    public static final Integer POSITION_SOLD_PRICE = 9;
    public static final Integer POSITION_SALE_FEES = 10;






    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement =
                "CREATE TABLE " + ITEMS_TABLE +"(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ITEM_NAME + " TEXT," +
                    COLUMN_PURCHASE_LOCATION + " TEXT," +
                    COLUMN_PURCHASE_PRICE + " FLOAT," +
                    COLUMN_PURCHASE_DATE + " TEXT," +
                    COLUMN_STORAGE_LOCATION + " TEXT," +
                    COLUMN_SOLD + " BOOL," +
                    COLUMN_SALE_DATE + " TEXT," +
                    COLUMN_PLATFORM_SOLD_ON + " TEXT," +
                    COLUMN_SOLD_PRICE + " TEXT," +
                    COLUMN_SALE_FEES + " FLOAT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }
    public ContentValues setContentValues(ContentValues cv, Item item){

        cv.put(COLUMN_ITEM_NAME, item.getItemName());
        cv.put(COLUMN_PURCHASE_LOCATION, item.getPurchasedFrom());
        cv.put(COLUMN_PURCHASE_PRICE, item.getPurchasePrice());
        cv.put(COLUMN_PURCHASE_DATE, item.getPurchaseDate());
        cv.put(COLUMN_STORAGE_LOCATION, item.getStorageLocation());
        cv.put(COLUMN_SOLD, item.getSold());
        cv.put(COLUMN_SALE_DATE, item.getSaleDate());
        cv.put(COLUMN_PLATFORM_SOLD_ON, item.getPlatformSoldOn());
        cv.put(COLUMN_SOLD_PRICE, item.getSoldPrice());
        cv.put(COLUMN_SALE_FEES, item.getSaleFees());

        return cv;
    }
    public ContentValues setContentValues2(ContentValues cv, Item item){
        cv.put(COLUMN_ID, item.getId());
        cv.put(COLUMN_ITEM_NAME, item.getItemName());
        cv.put(COLUMN_PURCHASE_LOCATION, item.getPurchasedFrom());
        cv.put(COLUMN_PURCHASE_PRICE, item.getPurchasePrice());
        cv.put(COLUMN_PURCHASE_DATE, item.getPurchaseDate());
        cv.put(COLUMN_STORAGE_LOCATION, item.getStorageLocation());
        cv.put(COLUMN_SOLD, item.getSold());
        cv.put(COLUMN_SALE_DATE, item.getSaleDate());
        cv.put(COLUMN_PLATFORM_SOLD_ON, item.getPlatformSoldOn());
        cv.put(COLUMN_SOLD_PRICE, item.getSoldPrice());
        cv.put(COLUMN_SALE_FEES, item.getSaleFees());

        return cv;
    }
    public void deleteItem(Context context, Item item){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String num = String.valueOf(item.getId());
        int val = sqLiteDatabase.delete(ITEMS_TABLE, COLUMN_ID + " = ?",new String[]{String.valueOf(item.getId())});
        if(val > 0){
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean addNewItem(Item newItem){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = setContentValues(new ContentValues(), newItem);


        long insert = sqLiteDatabase.insert(ITEMS_TABLE, null, cv);
        return insert != -1;
    }
    public Item getItem(Context context, int itemId){
        Item item;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] idString = {String.valueOf(itemId)};
        String queryString = "SELECT * FROM " + ITEMS_TABLE + " WHERE " + COLUMN_ID + "= ? ";
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, idString);
        if(cursor.moveToFirst()) {
            try {
                item = new Item(
                        cursor.getInt(POSITION_ID),
                        cursor.getString(POSITION_ITEM_NAME),
                        cursor.getString(POSITION_PURCHASE_LOCATION),
                        cursor.getString(POSITION_PURCHASE_DATE),
                        cursor.getFloat(POSITION_PURCHASE_PRICE),
                        cursor.getString(POSITION_STORAGE_LOCATION),
                        cursor.getInt(POSITION_SOLD) > 0,
                        cursor.getString(POSITION_SALE_DATE),
                        cursor.getString(POSITION_PLATFORM_SOLD_ON),
                        cursor.getFloat(POSITION_SOLD_PRICE),
                        cursor.getFloat(POSITION_SALE_FEES));
            } catch (Exception e) {
                e.printStackTrace();
                item = new Item("Error", "Error", "Error", 0, "Error");
            }
        }
        else{
            item = new Item("Error", "Error", "Error", 0, "Error");
            Toast.makeText(context, "ERROR: ITEM NOT FOUND IN DATABASE", Toast.LENGTH_LONG).show();
        }

        return item;
    }
    public void updateItem(Context context, Item item){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = setContentValues2(new ContentValues(), item);
        String num = String.valueOf(item.getId());
        int val = sqLiteDatabase.update(ITEMS_TABLE, cv, COLUMN_ID + "= ?",new String[]{String.valueOf(item.getId())});
        if(val > 0){
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<Item> getAllItems(){
        ArrayList<Item> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + ITEMS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int itemID = cursor.getInt(POSITION_ID);
                String itemName = cursor.getString(POSITION_ITEM_NAME);
                String purchaseLocation = cursor.getString(POSITION_PURCHASE_LOCATION);
                double purchasePrice = cursor.getDouble(POSITION_PURCHASE_PRICE);
                String purchaseDate = cursor.getString(POSITION_PURCHASE_DATE);
                String storageLocation = cursor.getString(POSITION_STORAGE_LOCATION);
                boolean sold = cursor.getInt(POSITION_SOLD) > 0;
                String platformSold = cursor.getString(POSITION_PLATFORM_SOLD_ON);
                String saleDate = cursor.getString(POSITION_SALE_DATE);
                double soldPrice = cursor.getDouble(POSITION_SOLD_PRICE);
                double sellingFees = cursor.getDouble(POSITION_SALE_FEES);

                Item item = new Item(itemID,itemName,purchaseLocation,purchaseDate,purchasePrice,storageLocation,sold,saleDate,platformSold,soldPrice, sellingFees);
                returnList.add(item);
            }while(cursor.moveToNext());
        }
        else{
            //empty database
        }
        cursor.close();
        db.close();
        return returnList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
