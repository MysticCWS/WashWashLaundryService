package com.example.washwashlaundryservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Payment extends AppCompatActivity {

    private TextView textwasher, textdryer, textfold, totalLaundry;
    private Button confirmPaymentButton;
    private RadioGroup paymentMethodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Get data from Intent
        Intent intent = getIntent();
        String cartID = intent.getStringExtra("cartID");

        String washerOption = intent.getStringExtra("washerOption");
        String dryerOption = intent.getStringExtra("dryerOption");
        String foldOption = intent.getStringExtra("foldOption");
        int total = intent.getIntExtra("totalAmount", 0);

        //navigation bottom
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page1) {
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.page2) {
                    startActivity(new Intent(getApplicationContext(), UserCart.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.page3) {
                    startActivity(new Intent(getApplicationContext(), UserHistory.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.page4) {
                    startActivity(new Intent(getApplicationContext(), UserProfile.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });

        textwasher = findViewById(R.id.textwasher);
        textdryer = findViewById(R.id.textdryer);
        textfold = findViewById(R.id.textfold);
        totalLaundry = findViewById(R.id.totalLaundry);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);

        textwasher.setText(washerOption);
        textdryer.setText(dryerOption);
        textfold.setText(foldOption);
        totalLaundry.setText("RM " + total);

        // Confirm Payment button click listener
        confirmPaymentButton.setOnClickListener(v -> {

            int selectedId = paymentMethodGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(Payment.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedPaymentMethod = selectedRadioButton.getText().toString();
            String washerOption1 = textwasher.getText().toString();
            String dryerOption1 = textdryer.getText().toString();
            String foldOption1 = textfold.getText().toString();
            String totalLaundry1 = totalLaundry.getText().toString();

            // Get the current time and date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentTime = dateFormat.format(calendar.getTime());
            // Generate OTP
            String otpMachine = generateOTP();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser firebaseuser = auth.getCurrentUser();
            String userID = firebaseuser.getUid();

            if (cartID != null) {
                // Update status in List Cart if cartID is not null
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("List Cart").child(cartID);
                myRef1.child("status").setValue("CheckOut");
            }

            // Proceed with payment processing and save order history
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("History");
            String historyID = myRef.push().getKey();

            UserHistoryClass classRefAdd = new UserHistoryClass(userID, historyID, washerOption1, dryerOption1, foldOption1, totalLaundry1, selectedPaymentMethod, currentTime, otpMachine);
            myRef.child(historyID).setValue(classRefAdd)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Payment Successful with " + selectedPaymentMethod, Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(), PaymentDetails.class);
                        intent1.putExtra("washerOption1", washerOption1);
                        intent1.putExtra("dryerOption1", dryerOption1);
                        intent1.putExtra("foldOption1", foldOption1);
                        intent1.putExtra("totalLaundry1", totalLaundry1);
                        intent1.putExtra("selectedPaymentMethod", selectedPaymentMethod);
                        intent1.putExtra("currentTime", currentTime);
                        intent1.putExtra("otpMachine", otpMachine);
                        startActivity(intent1);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Failed to save order.", Toast.LENGTH_SHORT).show();
                    });
        });

    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Generates a 6-digit OTP
        return String.valueOf(otp);
    }

    @Override
    public void onBackPressed() {
        // Do something when back button is pressed
        // You can call finish() to close the current activity
        super.onBackPressed();
    }
}