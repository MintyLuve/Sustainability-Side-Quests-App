package com.fro.sustainabilitysidequests;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;

public class BotActivity extends AppCompatActivity {
    // Declare global variables here \/
    Button home;
    Button shop;
    TextView completed;
    private RecyclerView recyclerView;
    private Button button;
    private EditText editText;

    // Creating a variable for Volley request queue.
    private RequestQueue requestQueue;

    // Creating variables for the list and adapter.
    private ArrayList<Model> list;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);

        // Instantiate variables here \/
        home = findViewById(R.id.home);
        shop = findViewById(R.id.shop);
        completed = findViewById(R.id.completed);

        // Setting completed to saved value
        completed.setText(Values.completed);

        // Changing pages
        home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        shop.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShopActivity.class)));

        recyclerView = findViewById(R.id.recyclerView);
        button = findViewById(R.id.enter);
        editText = findViewById(R.id.input);

        // Initializing the request queue.
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();

        // Creating a new array list.
        list = new ArrayList<>();

        // Adding click listener for send button.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking if user entered a message.
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(BotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();

                    // if the user hasn't chatted yet
                    if (!Values.clicked){
                        Values.clicked = true;
                        Values.points += 100;
                        Toast.makeText(BotActivity.this, "+100 Points", Toast.LENGTH_SHORT).show();
                        Values.completed = "(Already earned chat points for this week)";
                        String text = "(Earned chat points for this week) " +" +100 Points";
                        completed.setText(text);
                    }

                    return;
                }

                // Sending message to bot.
                sendMessage(editText.getText().toString());

                // Clearing edit text.
                editText.setText(" ");
            }
        });

        // Initializing adapter.
        adapter = new Adapter(list);

        // Setting up layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Setting adapter to RecyclerView.
        recyclerView.setAdapter(adapter);
    }
    @SuppressLint("NotifyDataSetChanged")
    private void sendMessage(String userMsg) {

        // Adding user message to list.
        list.add(new Model(userMsg, USER_KEY));
        adapter.notifyDataSetChanged();

        // URL for API call.
        String url = "url" + userMsg;

        // Creating request queue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Creating a JSON object request.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {

                        // Extracting response from JSON.
                        String botResponse = response.getString("cnt");
                        list.add(new Model(botResponse, BOT_KEY));

                        // Notifying adapter.
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();

                        // Handling error response from bot.
                        list.add(new Model("No response", BOT_KEY));
                        adapter.notifyDataSetChanged();
                    }
                },
                error -> {

                    // Handling errors.
                    list.add(new Model("Sorry, no response found", BOT_KEY));
                    Toast.makeText(BotActivity.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
                });

        // Adding request to queue.
        queue.add(jsonObjectRequest);
    }

    // Constants for user and bot.
    private static final String USER_KEY = "user";
    private static final String BOT_KEY = "bot";

}
