package com.example.washwashlaundryservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PaymentDetails extends AppCompatActivity {

    private TextView textwasher, textdryer, textfold, totalLaundry, methodPayment, date, otpMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        // Get data from Intent
        Intent intent = getIntent();
        String washerOption = intent.getStringExtra("washerOption1");
        String dryerOption = intent.getStringExtra("dryerOption1");
        String foldOption = intent.getStringExtra("foldOption1");
        String totalLaundry1 = intent.getStringExtra("totalLaundry1");
        String selectedPaymentMethod = intent.getStringExtra("selectedPaymentMethod");
        String currentTime = intent.getStringExtra("currentTime");
        String otpMachine1 = intent.getStringExtra("otpMachine");

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
        methodPayment = findViewById(R.id.methodPayment);
        date = findViewById(R.id.date);
        otpMachine = findViewById(R.id.otpMachine);

        textwasher.setText(washerOption);
        textdryer.setText(dryerOption);
        textfold.setText(foldOption);
        totalLaundry.setText(totalLaundry1);
        methodPayment.setText(selectedPaymentMethod);
        date.setText(currentTime);
        otpMachine.setText(otpMachine1);

    }

    @Override
    public void onBackPressed() {
        // Do something when back button is pressed
        // You can call finish() to close the current activity
        super.onBackPressed();
    }
}