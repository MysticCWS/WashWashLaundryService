package com.example.washwashlaundryservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        //navigation bottom
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.page2);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page1) {
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.page2) {

                    Toast.makeText(getApplicationContext(), "Already in Page", Toast.LENGTH_SHORT).show();
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

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserCartAdapter mAdapter = new UserCartAdapter(new LinkedList<UserCartClass>(), UserCart.this);
        mRecyclerView.setAdapter(mAdapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseuser = auth.getCurrentUser();
        String userID = firebaseuser.getUid();

        String status = "Pending";

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("List Cart");
        Query query1 = myRef.orderByChild("userID").equalTo(userID);
        Query query2 = myRef.orderByChild("status").equalTo(status);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot takerSnapshot) {
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot statusSnapshot) {
                        LinkedList<UserCartClass> listRef = new LinkedList<>();
                        // Loop through the filtered results and add them to the list
                        for (DataSnapshot takerData : takerSnapshot.getChildren()) {
                            for (DataSnapshot statusData : statusSnapshot.getChildren()) {
                                if (takerData.getKey().equals(statusData.getKey())) {
                                    UserCartClass classRef = statusData.getValue(UserCartClass.class);
                                    listRef.add(classRef);
                                    break; // Break after finding a match to avoid duplicates
                                }
                            }
                        }
                        // Update your adapter with the filtered data
                        mAdapter.listItem = listRef;
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError statusError) {
                        // Handle database error
                        Toast.makeText(UserCart.this, "Error: " + statusError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError takerError) {
                // Handle database error
                Toast.makeText(UserCart.this, "Error: " + takerError.getMessage(), Toast.LENGTH_SHORT).show();
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