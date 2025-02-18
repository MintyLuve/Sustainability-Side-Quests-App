package com.fro.sustainabilitysidequests;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {
    // Declare global variables here \/
    Button home;
    Button recycle;
    Button fnButton;
    Button rblxButton;
    Button valButton;
    Button lolButton;
    TextView points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // Instantiate variables here \/
        home = findViewById(R.id.home);
        recycle = findViewById(R.id.recycle);
        points = findViewById(R.id.points);
        fnButton = findViewById(R.id.fnButton);
        rblxButton = findViewById(R.id.rblxButton);
        valButton = findViewById(R.id.valButton);
        lolButton = findViewById(R.id.lolButton);

        // Setting points to Values.points
        String text = "You Have "+Values.points+" Points";
        points.setText(text);

        // Switching pages
        home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        recycle.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecycleActivity.class)));

        // Spending points
        fnButton.setOnClickListener(v -> spendPoints());
        rblxButton.setOnClickListener(v -> spendPoints());
        valButton.setOnClickListener(v -> spendPoints());
        lolButton.setOnClickListener(v -> spendPoints());
    }

    void spendPoints(){
        if (Values.points >= 100){
            Values.points -= 100;
            String text = "You Have "+Values.points+" Points";
            points.setText(text);
            Toast.makeText(this, "Points transferred!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Not enough points", Toast.LENGTH_SHORT).show();
        }
    }
}