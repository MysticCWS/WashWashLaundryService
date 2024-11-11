package com.example.washwashlaundryservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Wash extends AppCompatActivity {

    private int selectedWasherPrice = 0;

    // Prices for each option (adjust as needed)
    private final int washer15KgPrice = 5;
    private final int washer20KgPrice = 7;
    private final int washer30KgPrice = 10;

    private TextView totalLaundryText;

    private TextView textwasher;
    private ImageView imgaddtocart;
    private Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash);

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

        //BACK FUNCTION
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement back button functionality here
                onBackPressed(); // Call onBackPressed method to navigate back
            }
        });

        totalLaundryText = findViewById(R.id.totalLaundry);
        textwasher = findViewById(R.id.textwasher);

        // Set up click listeners for washer options
        RelativeLayout washer15KgLayout = findViewById(R.id.layout1);
        washer15KgLayout.setOnClickListener(v -> {
            selectedWasherPrice = washer15KgPrice;
            textwasher.setText("Washer: 15 KG (RM 5)");
            calculateTotal();
        });

        RelativeLayout washer20KgLayout = findViewById(R.id.layout2);
        washer20KgLayout.setOnClickListener(v -> {
            selectedWasherPrice = washer20KgPrice;
            textwasher.setText("Washer: 20 KG (RM 7)");
            calculateTotal();
        });

        RelativeLayout washer30KgLayout = findViewById(R.id.layout3);
        washer30KgLayout.setOnClickListener(v -> {
            selectedWasherPrice = washer30KgPrice;
            textwasher.setText("Washer: 30 KG (RM 10)");
            calculateTotal();
        });
    }

    private void calculateTotal() {
        int total = selectedWasherPrice;
        totalLaundryText.setText("RM " + total);

        imgaddtocart = findViewById(R.id.imgaddtocart);
        imgaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser firebaseuser = auth.getCurrentUser();
                String userID = firebaseuser.getUid();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("List Cart");
                String cartID = myRef.push().getKey();

                // Create a new LaundryOrder object
                String washerOption = textwasher.getText().toString();
                String dryerOption = "NONE";
                String foldOption = "NONE";
                String status = "Pending";

                UserCartClass classRefAdd = new UserCartClass(userID, cartID, washerOption, dryerOption, foldOption, total, status);
                myRef.child(cartID).setValue(classRefAdd)
                        .addOnSuccessListener(aVoid -> {
                            // Successfully saved to Firebase
                            Toast.makeText(getApplicationContext(),"Order saved to cart!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Failed to save to Firebase
                            Toast.makeText(getApplicationContext(), "Failed to save order.", Toast.LENGTH_SHORT).show();
                        });

            }
        });

        checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String washerOption = textwasher.getText().toString();
                String dryerOption = "NONE";
                String foldOption = "NONE";
                int totalAmount = selectedWasherPrice;

                Toast.makeText(getApplicationContext(), "Proceed to payment ...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                intent.putExtra("washerOption",washerOption);
                intent.putExtra("dryerOption",dryerOption);
                intent.putExtra("foldOption",foldOption);
                intent.putExtra("totalAmount",totalAmount);
                startActivity(intent);

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