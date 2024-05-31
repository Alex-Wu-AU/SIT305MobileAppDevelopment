package com.example.learningexperience;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        // Initialize views
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        Button shareButton = findViewById(R.id.shareButton);
        Button upgradeButton = findViewById(R.id.upgradeButton);

        // Dummy data for username and email (you can replace it with actual data)
        usernameTextView.setText("Username");
        emailTextView.setText("user@example.com");

        // Generate QR code and display it
        String profileData = generateProfileData();
        Bitmap qrCodeBitmap = generateQRCode(profileData);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);

        // Share button click listener
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareProfileData(profileData);
            }
        });

        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UpgradeActivity.class);
                startActivity(intent);
            }
        });
    }

    private String generateProfileData() {
        // Generate profile data (you can customize this according to your needs)
        String username = "Username";
        String email = "user@example.com";
        int currentScore = getIntent().getIntExtra("currentScore", 0);
        int questionAttempted = getIntent().getIntExtra("questionAttempted", 0);

        return String.format(Locale.getDefault(), "Username: %s\nEmail: %s\nCurrent Score: %d\nQuestions Attempted: %d",
                username, email, currentScore, questionAttempted);
    }

    private Bitmap generateQRCode(String data) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 400, 400);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void shareProfileData(String profileData) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, profileData);
        startActivity(Intent.createChooser(shareIntent, "Share Profile Data"));
    }

}
