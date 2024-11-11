package com.example.washwashlaundryservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class UserHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        //navigation bottom
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.page3);
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
                    Toast.makeText(getApplicationContext(), "Already in Page", Toast.LENGTH_SHORT).show();
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
        UserHistoryAdapter mAdapter = new UserHistoryAdapter(new LinkedList<UserHistoryClass>(), UserHistory.this);
        mRecyclerView.setAdapter(mAdapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseuser = auth.getCurrentUser();
        String userID = firebaseuser.getUid();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("History");
        Query query = myRef.orderByChild("userID").equalTo(userID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<UserHistoryClass> listData = new LinkedList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserHistoryClass classRef = dataSnapshot.getValue(UserHistoryClass.class);
                    listData.add(classRef);
                }
                mAdapter.listItem = listData;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(UserHistory.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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