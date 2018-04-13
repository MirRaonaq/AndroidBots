package com.example.fatin.foodbasket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ClaimReport extends AppCompatActivity {

    Button userClaim;
    private ImageView singleImage;
    private TextView singleBuilding;
    private TextView singleRoom;
    private TextView singleDescription;
    private String PostKey=null;
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_report);

        /*databaseReference=FirebaseDatabase.getInstance().getReference("users").child("photos").child(PostKey);
        PostKey = getIntent().getExtras().getString("postKey");

        singleBuilding = findViewById(R.id.singleBuildingName);
        singleRoom = findViewById(R.id.singleRoomNumber);
        singleImage = findViewById(R.id.singleImage);
        singleDescription = findViewById(R.id.singleDescription);

        databaseReference.child(PostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String postBuilding = (String)dataSnapshot.child("buildingName").getValue();
                String postRoom = (String) dataSnapshot.child("roomNum").getValue();
                String postDsc = (String) dataSnapshot.child("description").getValue();
                String postImage=(String) dataSnapshot.child("image").getValue();

                singleBuilding.setText(postBuilding);
                singleRoom.setText(postRoom);
                singleDescription.setText(postDsc);
                Picasso.get().load(postImage).into(singleImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        userClaim = findViewById(R.id.userClaim);
        userClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle counter increment here
                Log.d("Food","Button pressed");

            }
        });

    }
}
