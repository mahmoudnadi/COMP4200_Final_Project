package com.example.a4200project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class NewItem extends AppCompatActivity {
    Button createItem, cancelAdd;
    TextView newItemTitle;
    EditText itemNameInput, purchaseLocationInput, storageLocationInput;
    EditText purchasePriceInput, purchaseDateInput;
    int year;
    int month;
    int day;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_item);

        newItemTitle = findViewById(R.id.newItemTitle);
        createItem = findViewById(R.id.addItemButton);
        itemNameInput = findViewById(R.id.itemNameInput);
        purchaseDateInput = findViewById(R.id.purchaseDateInput);
        purchaseLocationInput = findViewById(R.id.purchaseLocationInput);
        purchasePriceInput = findViewById(R.id.purchasePriceInput);
        storageLocationInput = findViewById(R.id.storageLocationInput);
        cancelAdd = findViewById(R.id.cancelAdd);
        Calendar calendar = Calendar.getInstance();
         year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        purchaseDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewItem.this, (datePicker, year, month, day) -> {
                    month++;
                    date = day + "/" + month + "/" + year;
                    purchaseDateInput.setText(date);
                }, year, month,day);
                datePickerDialog.show();
            }
        });
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewItem.this);

                builder.setTitle("Any changes made will be lost");
                builder.setMessage("Are you sure you want to leave?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(NewItem.this, MainActivity.class));
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
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item newItem;
                try {
                    newItem = new Item(itemNameInput.getText().toString(), purchaseLocationInput.getText().toString(), purchaseDateInput.getText().toString(), Double.parseDouble(purchasePriceInput.getText().toString()), storageLocationInput.getText().toString());
                } catch (NumberFormatException e) {
                    newItem = new Item("Error", "Error", date, 0, "error");
                }

                DBHelper dbHelper = new DBHelper(NewItem.this);
                boolean b = dbHelper.addNewItem(newItem);

                Intent goToListView = new Intent(NewItem.this, MainActivity.class);
                startActivity(goToListView);
            }
        });

    }
}