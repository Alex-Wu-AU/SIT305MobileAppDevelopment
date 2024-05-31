package com.example.learningexperience;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        // Initialize views and set data
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView);
        TextView correctlyAnsweredTextView = findViewById(R.id.correctlyAnsweredTextView);
        TextView incorrectAnswersTextView = findViewById(R.id.incorrectAnswersTextView);
        Button shareButton = findViewById(R.id.shareButton);

        // Set data (This should be retrieved from your data source)
        usernameTextView.setText("Username");
        emailTextView.setText("user@example.com");
        totalQuestionsTextView.setText("Total Questions: 10");
        correctlyAnsweredTextView.setText("Correctly Answered: 10");
        incorrectAnswersTextView.setText("Incorrect Answers: 10");

//        shareButton.setOnClickListener(view -> {
//            // Navigate to ShareProfileActivity
//            Intent intent = new Intent(ProfileActivity.this, ShareProfileActivity.class);
//            startActivity(intent);
//        });
    }
}
