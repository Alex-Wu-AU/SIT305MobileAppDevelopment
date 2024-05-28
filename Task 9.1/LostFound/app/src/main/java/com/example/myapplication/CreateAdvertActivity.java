package com.example.myapplication;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class CreateAdvertActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, descriptionEditText, dateEditText, locationEditText;
    private RadioGroup postTypeRadioGroup;
    private double latitude, longitude;

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

        String apiKey = getMetaDataValue("com.google.android.geo.API_KEY");
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        locationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getApplicationContext());
                startAutocomplete.launch(intent);
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveAdvert());
    }

    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        Log.i("TAG", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());

                        locationEditText.setText(place.getName());
                        latitude = place.getLatLng().latitude;
                        longitude = place.getLatLng().longitude;
                    }
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Log.i("TAG", "User canceled autocomplete");
                }
            });

    private void saveAdvert() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String location = locationEditText.getText().toString();
        int selectedTypeId = postTypeRadioGroup.getCheckedRadioButtonId();
        String postType = (selectedTypeId == R.id.lost_radio_button) ? "Lost" : "Found";

        Advert advert = new Advert(postType, name, phone, description, date, location, latitude, longitude);
        AdvertDatabaseHelper dbHelper = new AdvertDatabaseHelper(this);
        dbHelper.addAdvert(advert);

        finish();
    }

    private String getMetaDataValue(String name) {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
