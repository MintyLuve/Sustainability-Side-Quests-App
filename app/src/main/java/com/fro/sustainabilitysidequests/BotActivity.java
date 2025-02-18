package com.fro.sustainabilitysidequests;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BotActivity extends AppCompatActivity {
    // Declare global variables here \/
    Button home;
    Button shop;
    Button enter;
    View placeholder;
    LinearLayout response;
    LinearLayout yourLayout;
    TextView yourText;
    TextView completed;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);

        // Instantiate variables here \/
        home = findViewById(R.id.home);
        shop = findViewById(R.id.shop);
        enter = findViewById(R.id.enter);
        placeholder = findViewById(R.id.placeholder);
        response = findViewById(R.id.response);
        yourLayout = findViewById(R.id.yourLayout);
        yourText = findViewById(R.id.yourText);
        completed = findViewById(R.id.completed);
        input = findViewById(R.id.input);

        // Changing pages
        home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        shop.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShopActivity.class)));

        // Setting completed to saved value
        completed.setText(Values.completed);
        setMessage(Values.clicked);

        // Mock chatbot
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().toString().isEmpty() && !Values.clicked){
                    Values.clicked = true;
                    Values.input = input.getText().toString();
                    setMessage(Values.clicked);
                    Values.completed = "(Weekly chat is completed)";
                    String text = Values.completed +" +100 Points";
                    completed.setText(text);
                    Values.points += 100;
                }
                else if (!Values.clicked) {
                    input.setHint("Please enter text");
                }
            }
        });
    }

    public void setMessage(boolean val){
        if (val){
            String text = Values.input;
            yourText.setText(text);
            yourLayout.setVisibility(VISIBLE);
            response.setVisibility(VISIBLE);

            //sets the empty view to have a lower weight
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f
            );
            placeholder.setLayoutParams(param);

            Values.clicked = true;
            input.setHint("Coming soon!");
        }
    }
}