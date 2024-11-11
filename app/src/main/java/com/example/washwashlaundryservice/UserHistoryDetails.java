package com.example.washwashlaundryservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserHistoryDetails extends AppCompatActivity {

    private TextView textwasher, textdryer, textfold, totalLaundry, methodPayment, date, otpMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_details);

        // Get data from Intent
        Intent intent = getIntent();
        String historyID = intent.getStringExtra("historyID");

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("History").child(historyID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the data and set the values of the views in the layout
                UserHistoryClass classRef = snapshot.getValue(UserHistoryClass.class);
                textwasher.setText(classRef.getWasherOption());
                textdryer.setText(classRef.getDryerOption());
                textfold.setText(classRef.getFoldOption());
                totalLaundry.setText(classRef.getTotalLaundry());
                methodPayment.setText(classRef.getPaymentMethod());
                date.setText(classRef.getDate());
                otpMachine.setText(classRef.getOtpMachine());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(UserHistoryDetails.this, "Error loading details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do something when back button is pressed
        // You can call finish() to close the current activity
        super.onBackPressed();
    }
}