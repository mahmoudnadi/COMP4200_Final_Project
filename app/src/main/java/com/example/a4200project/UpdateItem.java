package com.example.a4200project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UpdateItem extends AppCompatActivity {
    EditText itemName, purchaseLocation, purchaseDate, purchasePrice, storageLocation;
    EditText soldPrice, platformSold, soldDate, sellingFees;
    Switch soldSwitch;
    LinearLayout soldLayout;
    Button updateButton, deleteButton, cancelButton;
    ImageButton editButton;
    int itemId;
    Item item, modifiedItem;
    boolean sold= false;
    ArrayList<Item> itemArrayList;
    DatePickerDialog.OnDateSetListener setListener;
    int year;
    int month;
    int day;
    String date;
    Intent goToDisplayItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update_item);
        goToDisplayItems = new Intent(UpdateItem.this, DisplayItems.class);
        itemName = findViewById(R.id.itemName);
        purchaseLocation = findViewById(R.id.purchaseLocation);
        purchaseDate = findViewById(R.id.purchaseDate);
        purchasePrice = findViewById(R.id.purchasePrice);
        storageLocation = findViewById(R.id.storageLocation);
        soldPrice = findViewById(R.id.soldPrice);
        soldDate = findViewById(R.id.soldDate);
        platformSold = findViewById(R.id.platformSold);
        soldSwitch = findViewById(R.id.soldSwitch);
        soldLayout = findViewById(R.id.SoldLayout);
        sellingFees = findViewById(R.id.sellingFees);
        cancelButton = findViewById(R.id.cancelButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        soldSwitch.setClickable(false);
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
        getCurrentItem();
        editButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                editButton.setVisibility(View.INVISIBLE);
                soldSwitch.setClickable(true);
                deleteButton.setEnabled(true);
                deleteButton.setBackgroundColor(Color.RED);
                updateButton.setEnabled(true);
                updateButton.setBackgroundColor(Color.parseColor("#00A4FF"));
                itemName.setFocusableInTouchMode(true);
                purchaseLocation.setFocusableInTouchMode(true);
                purchaseDate.setFocusableInTouchMode(true);
                purchasePrice.setFocusableInTouchMode(true);
                storageLocation.setFocusableInTouchMode(true);
                soldPrice.setFocusableInTouchMode(true);
                soldDate.setFocusableInTouchMode(true);
                platformSold.setFocusableInTouchMode(true);
                soldSwitch.setFocusableInTouchMode(true);
                soldLayout.setFocusableInTouchMode(true);
                sellingFees.setFocusableInTouchMode(true);

            }
        });

        soldSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSold) {
                sold = isSold;
                if(isSold){
                    soldSwitch.setVisibility(View.GONE);
                    soldLayout.setVisibility(View.VISIBLE);

                }else{
                    soldLayout.setVisibility(View.INVISIBLE);
                    sold = true;
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateItem.this);

                builder.setTitle("Any changes made will be lost");
                builder.setMessage("Are you sure you want to leave?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(goToDisplayItems);
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateItem.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to update this item?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        setCurrentItem();
                        DBHelper db = new DBHelper(UpdateItem.this);
                        db.updateItem(UpdateItem.this, modifiedItem);
                        startActivity(goToDisplayItems);
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateItem.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete this item?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        setCurrentItem();
                        DBHelper db = new DBHelper(UpdateItem.this);
                        db.deleteItem(UpdateItem.this, modifiedItem);
                        startActivity(goToDisplayItems);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        purchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateItem.this, (datePicker, year, month, day) -> {
                    month++;
                    date = day + "/" + month + "/" + year;
                    purchaseDate.setText(date);
                }, year, month,day);
                datePickerDialog.show();
            }
        });
        soldDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateItem.this, (datePicker, year, month, day) -> {
                    month++;
                    date = day + "/" + month + "/" + year;
                    soldDate.setText(date);
                }, year, month,day);
                datePickerDialog.show();
            }
        });

    }
    public void getCurrentItem(){
        itemId = getIntent().getIntExtra("itemId", 0);
        DBHelper db = new DBHelper(UpdateItem.this);
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en","CA"));
        decimalFormat.applyPattern("0.00");
        item = db.getItem(UpdateItem.this,itemId);
        itemName.setText(item.getItemName());
        purchaseLocation.setText(item.getPurchasedFrom());
        purchaseDate.setText(item.getPurchaseDate());
        purchasePrice.setText("$" + decimalFormat.format(item.getPurchasePrice()));
        storageLocation.setText(item.getStorageLocation());
        if(item.getSold()){
            soldSwitch.setChecked(true);
            soldSwitch.setVisibility(View.GONE);
            soldLayout.setVisibility(View.VISIBLE);
        }
        soldDate.setText(item.getSaleDate());
        platformSold.setText(item.getPlatformSoldOn());
        soldPrice.setText("$" + decimalFormat.format(item.getSoldPrice()));
        sellingFees.setText("$" + decimalFormat.format(item.getSaleFees()));
    }

    public void setCurrentItem(){
        modifiedItem = new Item(
                itemId,
                itemName.getText().toString(),
                purchaseLocation.getText().toString(),
                purchaseDate.getText().toString(),
                Double.parseDouble((new StringBuilder()).append(purchasePrice.getText().toString()).deleteCharAt(0).toString()),
                storageLocation.getText().toString(),
                sold,
                soldDate.getText().toString(),
                platformSold.getText().toString(),
                Double.parseDouble((new StringBuilder()).append(soldPrice.getText().toString()).deleteCharAt(0).toString()),
                Double.parseDouble((new StringBuilder()).append(sellingFees.getText().toString()).deleteCharAt(0).toString())
        );
    }

}