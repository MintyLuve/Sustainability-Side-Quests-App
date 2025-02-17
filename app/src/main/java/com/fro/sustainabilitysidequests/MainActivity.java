package com.fro.sustainabilitysidequests;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Declare global variables here \/
    Button recycle;
    Button learn;
    Button chat;
    Button shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate variables here \/
        recycle = findViewById(R.id.recycle);
        learn = findViewById(R.id.learn);
        chat = findViewById(R.id.chat);
        shop = findViewById(R.id.shop);

        // Changing pages
        recycle.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecycleActivity.class)));
        learn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QuestsActivity.class)));
        chat.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), BotActivity.class)));
        shop.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShopActivity.class)));
    }

}