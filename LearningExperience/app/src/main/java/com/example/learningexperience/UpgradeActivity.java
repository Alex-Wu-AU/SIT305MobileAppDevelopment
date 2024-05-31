package com.example.learningexperience;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.view.CardInputWidget;

public class UpgradeActivity extends AppCompatActivity {
    private static final String CLIENT_SECRET = "Customer";
    private CardInputWidget cardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_account_page);

        // Initialize PaymentSheet
        PaymentConfiguration.init(getApplicationContext(), "pk_test_51Nz8zYIf8xxUPP1AN9eTbxbqkgN1Jjletn548a7hjpTbM6b8t9mVliSKgTJCrCnDIHuZ4AuuoQdiw2bEFxnLf9Gi00k0UQ6UFu");

        // Set up PaymentSheet
        PaymentSheet paymentSheet = new PaymentSheet(this, paymentResult -> {
            if (paymentResult instanceof PaymentSheetResult.Completed) {
                // Payment was successful
                Toast.makeText(UpgradeActivity.this, "Upgrade successful", Toast.LENGTH_SHORT).show();
            } else if (paymentResult instanceof PaymentSheetResult.Canceled) {
                // Payment was canceled by the user
                Toast.makeText(UpgradeActivity.this, "Upgrade canceled", Toast.LENGTH_SHORT).show();
            } else if (paymentResult instanceof PaymentSheetResult.Failed) {
                // Payment failed
                Throwable error = ((PaymentSheetResult.Failed) paymentResult).getError();
                Toast.makeText(UpgradeActivity.this, "Upgrade failed: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Find the root layout of the activity
        View rootView = findViewById(android.R.id.content);

        // Inflate the card input widget layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardInputLayout = inflater.inflate(R.layout.card_input_layout, null);
        cardInputWidget = cardInputLayout.findViewById(R.id.cardInputWidget);

        // Show PaymentSheet and the inflated card input widget when any of the upgrade buttons are clicked
        findViewById(R.id.starterButton).setOnClickListener(v -> {
            paymentSheet.presentWithPaymentIntent(CLIENT_SECRET);
            showCardInputWidget();
        });

        findViewById(R.id.intermediateButton).setOnClickListener(v -> {
            paymentSheet.presentWithPaymentIntent(CLIENT_SECRET);
            showCardInputWidget();
        });

        findViewById(R.id.advancedButton).setOnClickListener(v -> {
            paymentSheet.presentWithPaymentIntent(CLIENT_SECRET);
            showCardInputWidget();
        });
    }

    // Method to add the inflated card input widget to the root layout
    private void showCardInputWidget() {
        // Find the root layout of the activity
        ViewGroup rootView = findViewById(android.R.id.content);

        // Add the inflated card input widget to the root layout
        rootView.addView(cardInputWidget);
    }
}
