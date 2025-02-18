package com.fro.sustainabilitysidequests;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuestsActivity extends AppCompatActivity {
    // Declare global variables here \/
    Button home;
    Button shop;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests);

        // Instantiate variables here \/
        home = findViewById(R.id.home);
        shop = findViewById(R.id.shop);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);

        // Changing pages
        home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        shop.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShopActivity.class)));

        // Adding points
        b1.setOnClickListener(v -> addPoints(1));
        b2.setOnClickListener(v -> addPoints(2));
        b3.setOnClickListener(v -> addPoints(3));
        b4.setOnClickListener(v -> addPoints(4));
        b5.setOnClickListener(v -> addPoints(5));
        b6.setOnClickListener(v -> addPoints(6));
        b7.setOnClickListener(v -> addPoints(7));
    }

    void addPoints(int key){
        if (!Values.arrayList.contains(key)){
            Values.points += 100;
            Values.arrayList.add(key);
            Toast.makeText(this, "+100 Points", Toast.LENGTH_SHORT).show();
        }
    }
}