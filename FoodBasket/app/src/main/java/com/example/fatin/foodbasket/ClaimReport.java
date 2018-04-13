package com.example.fatin.foodbasket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ClaimReport extends AppCompatActivity {

    Button userClaim;
    Button userReport;
    DatabaseReference claimCountRef;
    DatabaseReference reportCountRef;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private boolean userClaimed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_report);

        userClaim = findViewById(R.id.userClaim);
        userClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle counter increment here

                if (userClaimed) {
                    claimCountRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("photos").hasChild("claimed")) {

                                Log.d("FoodBasket","Button works");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        userReport = findViewById(R.id.userReport);
        userReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
