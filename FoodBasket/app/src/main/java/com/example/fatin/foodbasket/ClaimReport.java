package com.example.fatin.foodbasket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    //private ImageView singleImage;
    private String PostKey=null;
    private DatabaseReference databaseReference;
    //FirebaseAuth mAuth;
    //FirebaseAuth.AuthStateListener mAuthListener;
    static String TAG = "testpost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_report);
        PostKey = getIntent().getExtras().getString("postKey");
        Log.d(TAG, "onCreate: "+PostKey);
        databaseReference=FirebaseDatabase.getInstance().getReference("users").child("photos").child(PostKey);
        //singleImage = findViewById(R.id.singleImage);

        databaseReference.child(PostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //This is to get the image from to the claim button page.
                /*String postImage=(String) dataSnapshot.child("image").getValue();
                Picasso.get().load(postImage).into(singleImage);*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userClaim = findViewById(R.id.userClaim);
        userClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle counter increment here
                databaseReference.removeValue();
                Toast.makeText(ClaimReport.this,"Claimed",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ClaimReport.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
