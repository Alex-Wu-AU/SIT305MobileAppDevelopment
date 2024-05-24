package com.example.chatbot;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameInput = findViewById(R.id.nameInput);
        Button startChatButton = findViewById(R.id.startChatButton);

        startChatButton.setOnClickListener(view -> {
            String username = nameInput.getText().toString().trim();
            if (!username.isEmpty()) {
                startChatActivity(username);
            } else {
                nameInput.setError("Please enter your name");
            }
        });
    }

    private void startChatActivity(String username) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
}
