package com.example.a4200project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayItems extends AppCompatActivity {
    ArrayList<Item> itemArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    Button goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_display_items);
        recyclerView = findViewById(R.id.customListView);
        goHome = findViewById(R.id.homeButton);
        DBHelper db = new DBHelper(DisplayItems.this);
        itemArrayList = db.getAllItems();

        adapter = new RecyclerAdapter(DisplayItems.this, itemArrayList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayItems.this));

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(DisplayItems.this, MainActivity.class);
                startActivity(home);
            }
        });
    }
}