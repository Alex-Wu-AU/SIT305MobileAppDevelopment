package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAdvertActivity extends AppCompatActivity {
    private EditText nameEditText, phoneEditText, descriptionEditText, dateEditText, locationEditText;
    private RadioGroup postTypeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        nameEditText = findViewById(R.id.name_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        dateEditText = findViewById(R.id.date_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        postTypeRadioGroup = findViewById(R.id.post_type_radio_group);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveAdvert());
    }

    private void saveAdvert() {
        // Collect data from input fields
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String location = locationEditText.getText().toString();
        int selectedTypeId = postTypeRadioGroup.getCheckedRadioButtonId();
        String postType = (selectedTypeId == R.id.lost_radio_button) ? "Lost" : "Found";

        // Save data to the database (SQLite)
        Advert advert = new Advert(postType, name, phone, description, date, location);
        AdvertDatabaseHelper dbHelper = new AdvertDatabaseHelper(this);
        dbHelper.insertAdvert(advert);

        // After saving, navigate back to the main screen
        finish();
    }
}

