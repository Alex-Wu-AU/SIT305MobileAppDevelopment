package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView itemName;
    private TextView itemDetails;
    private Button removeButton;
    private Advert advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        itemName = findViewById(R.id.item_name);
        itemDetails = findViewById(R.id.item_details);
        removeButton = findViewById(R.id.remove_button);

        // Retrieve Advert object from intent
        advert = (Advert) getIntent().getSerializableExtra("Advert");

        if (advert != null) {
            itemName.setText(advert.getName());
            itemDetails.setText(String.format("Phone: %s\nDescription: %s\nDate: %s\nLocation: %s",
                    advert.getPhone(), advert.getDescription(), advert.getDate(), advert.getLocation()));
        }

        removeButton.setOnClickListener(v -> {
            // Remove the advert from the SQLite database
            AdvertDatabaseHelper dbHelper = new AdvertDatabaseHelper(this);
            dbHelper.deleteAdvertByName(advert.getName());

            finish();
        });
    }
}
