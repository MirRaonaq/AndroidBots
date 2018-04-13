package com.example.fatin.foodbasket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ClaimReport extends AppCompatActivity {

    Button userClaim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_report);

        userClaim =findViewById(R.id.userClaim);
        userClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClaimReport.this,PostedImages.class);
                startActivity(intent);
                Toast.makeText(ClaimReport.this, "Item claimed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
