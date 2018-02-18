package com.example.fatin.foodbasket;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the button by its id from XML file(shareButton)
        Button button = findViewById(R.id.shareButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Main2Activity();
                //Log.d is to see if the button is actually working or not
                Log.d("FoodBasket", "Share button pressed");
            }
        });

        Button button1 = findViewById(R.id.claimButton);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view3){
                Main4Activity(view3);

            }
        });
    }
    public void Main2Activity(){
        //Open the user input page or second acitivity
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);

    }

    public void Main4Activity(View view3){
        Intent intent3 = new Intent(this,Main4Activity.class);
        startActivity(intent3);

    }
}
