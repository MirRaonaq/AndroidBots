package com.example.fatin.foodbasket;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Main4Activity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    startActivity(new Intent(Main4Activity.this, MainActivity.class));
                } else if (i == 2) {
                    startActivity(new Intent(Main4Activity.this, Main3Activity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
        //admin.au

     /*   Task<String> task =FirebaseAuth.getInstance().createCustomTokenAsync("String")

        FirebaseAuth.getInstance().deleteUserAsync(uid).get();
        System.out.println("Successfully deleted user.");*/
    }



