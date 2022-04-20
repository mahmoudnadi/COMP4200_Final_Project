package com.example.a4200project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> itemArrayList = new ArrayList<Item>();
    TextView mainTitle, mainSubtitle, salesTitle, inventoryTitle, totalQuantity,totalNumOfSales, yearlySales, yearlyExpenses, yearlyProfits;
    ImageView inventoryCardImage, salesCardImage;
    CardView salesCard, inventoryCard;
    FloatingActionButton newAction;
    private int totalItems = 0;
    private int numOfSales = 0;
    private double totalSales = 0;
    private double totalExpenses = 0;
    private double totalProfit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mainTitle = findViewById(R.id.mainTitle);
        mainSubtitle = findViewById(R.id.mainSubtitle);
        salesTitle = findViewById(R.id.salesTitle);
        inventoryTitle = findViewById(R.id.inventoryTitle);
        newAction = findViewById(R.id.newAction);
        salesCard = findViewById(R.id.salesCard);
        inventoryCard = findViewById(R.id.inventoryCard);
        inventoryCardImage = findViewById(R.id.inventoryCardImage);
        salesCardImage = findViewById(R.id.inventoryCardImage);
        yearlySales = findViewById(R.id.yearlySales);
        totalQuantity = findViewById(R.id.totalQuantity);
        yearlyExpenses = findViewById(R.id.yearlyExpense);
        yearlyProfits = findViewById(R.id.yearlyProfits);
        totalNumOfSales = findViewById(R.id.totalNumOfSales);
        String sTitle = "Sales Summary";
        SpannableString content = new SpannableString(sTitle);
        content.setSpan(new UnderlineSpan(),0,sTitle.length(),0);
        salesTitle.setText(content);

        String invTitle = "Inventory Summary";
        SpannableString content2 = new SpannableString(invTitle);
        content2.setSpan(new UnderlineSpan(),0,invTitle.length(),0);
        inventoryTitle.setText(content2);

        DBHelper db = new DBHelper(MainActivity.this);
        itemArrayList = db.getAllItems();
        for(Item currentItem : itemArrayList){
            if(currentItem.sold){
                totalSales += currentItem.getSoldPrice();
                numOfSales++;
            }
            totalExpenses = totalExpenses + currentItem.getPurchasePrice() + currentItem.getSaleFees();
            totalItems++;
        }
        totalProfit = totalSales - totalExpenses;
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en","CA"));
        decimalFormat.applyPattern("0.00");
        totalQuantity.setText("Total Number Of Items: " + totalItems);
        totalNumOfSales.setText("Total Number Of Sales: " + numOfSales);
        yearlySales.setText("Total Yearly Sales: $" + decimalFormat.format(totalSales));
        yearlyExpenses.setText("Total Yearly Expenses: $" + decimalFormat.format(totalExpenses));
        yearlyProfits.setText("Total Yearly Profits: $" + decimalFormat.format(totalProfit));

        inventoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent displayItemList = new Intent(MainActivity.this, DisplayItems.class);
                startActivity(displayItemList);
            }
        });

        newAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemActivity = new Intent(MainActivity.this, NewItem.class);
                startActivity(addItemActivity);
            }
        });
    }
}