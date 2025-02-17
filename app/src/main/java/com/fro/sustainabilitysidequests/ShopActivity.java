package com.fro.sustainabilitysidequests;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShopActivity extends AppCompatActivity {
    // Declare global variables here \/
    Button home;
    Button recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // Instantiate variables here \/
        home = findViewById(R.id.home);
        recycle = findViewById(R.id.recycle);

        // Switching pages
        home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        recycle.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecycleActivity.class)));
    }
}