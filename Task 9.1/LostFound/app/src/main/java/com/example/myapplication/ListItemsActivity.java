package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListItemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdvertAdapter adapter;
    private List<Advert> adverts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch items from the database
        AdvertDatabaseHelper dbHelper = new AdvertDatabaseHelper(this);
        adverts = dbHelper.getAllAdverts();

        // Initialize and set up the adapter
        adapter = new AdvertAdapter(adverts, this);
        recyclerView.setAdapter(adapter);

        // Set OnClickListener for the return button
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(v -> {
            // Navigate back to the MainActivity
            finish();
        });
    }


}


