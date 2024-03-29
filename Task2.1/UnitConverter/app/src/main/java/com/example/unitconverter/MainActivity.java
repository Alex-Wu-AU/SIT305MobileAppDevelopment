package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Spinner sourceUnitSpinner, destinationUnitSpinner;
    private EditText valueInput;
    private Button convertButton;
    private TextView resultOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sourceUnitSpinner = findViewById(R.id.source_unit_spinner);
        destinationUnitSpinner = findViewById(R.id.destination_unit_spinner);
        valueInput = findViewById(R.id.value_input);
        convertButton = findViewById(R.id.convert_button);
        resultOutput = findViewById(R.id.result_output);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.length_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceUnitSpinner.setAdapter(adapter);
        destinationUnitSpinner.setAdapter(adapter);

        //convert the value when button is clicked
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertValue();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void convertValue() {
        // Get selected units
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();

        // Get input value
        double inputValue = Double.parseDouble(valueInput.getText().toString());

        // Perform conversion logic
        double convertedValue = 0;

        // Convert the input value to meters
        double valueInMeters = convertToMeters(inputValue, sourceUnit);

        // Convert the value in meters to the destination unit
        convertedValue = convertFromMeters(valueInMeters, destinationUnit);

        // Display the result
        resultOutput.setText(String.valueOf(convertedValue));
    }

    private double convertToMeters(double value, String unit) {
        switch (unit) {
            case "Inches":
                return value * 0.0254; // 1 inch = 0.0254 meters
            case "Feet":
                return value * 0.3048; // 1 foot = 0.3048 meters
            case "Yards":
                return value * 0.9144; // 1 yard = 0.9144 meters
            case "Miles":
                return value * 1609.34; // 1 mile = 1609.34 meters
            case "Centimeters":
                return value * 0.01; // 1 centimeter = 0.01 meters
            case "Meters":
                return value; // No conversion needed
            case "Kilometers":
                return value * 1000; // 1 kilometer = 1000 meters
            default:
                return 0; // Invalid unit
        }
    }

    private double convertFromMeters(double valueInMeters, String unit) {
        switch (unit) {
            case "Inches":
                return valueInMeters / 0.0254; // 1 inch = 0.0254 meters
            case "Feet":
                return valueInMeters / 0.3048; // 1 foot = 0.3048 meters
            case "Yards":
                return valueInMeters / 0.9144; // 1 yard = 0.9144 meters
            case "Miles":
                return valueInMeters / 1609.34; // 1 mile = 1609.34 meters
            case "Centimeters":
                return valueInMeters / 0.01; // 1 centimeter = 0.01 meters
            case "Meters":
                return valueInMeters; // No conversion needed
            case "Kilometers":
                return valueInMeters / 1000; // 1 kilometer = 1000 meters
            default:
                return 0; // Invalid unit
        }
    }

}