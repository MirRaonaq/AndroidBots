package com.example.fatin.foodbasket;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    Button shareBtn =null;
    Button logoutBtn=null;
    Button claimBtn=null;
    Button openMapButton;

    TextView profile;
    FirebaseUser user;
    private static final String TAG = "shareButton";
    private static final String TAG2 = "mapButton"; //This is to test if map functionality is working


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        profile =(TextView)findViewById(R.id.profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String currUser = user.getEmail();
        String u = currUser.split("@")[0];
        profile.setText(u);
        //Toast.makeText(this,"current user is "+user,Toast.LENGTH_LONG).show();


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                  //  startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else if (i == 2) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(new Intent(MainActivity.this, Main3Activity.class));
                }else if (i==3){
                    deactivateAccount();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        shareBtn= (Button)findViewById(R.id.shareButton);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main2Activity();
                Log.d("FoodBasket", "Share button pressed");
            }
        });

        claimBtn = findViewById(R.id.claimButton);
        claimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Main4Activity();
                Log.d("FoodBasket", "Claim button pressed");
            }
        });
        openMapButton = findViewById(R.id.mapButton);
        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("map","map button");
                /*Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);*/

            }
        });
    }

    @AfterPermissionGranted(123)
    private void main2Activity(){
        String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this,permissions)){
            Log.d(TAG,"Already has permission");
            Intent intent = new Intent(this,ImageCapture.class);
            startActivity(intent);

        }
        else EasyPermissions.requestPermissions(this,"This app requires location and camera permissions to take picture and know where it is posted from",
                123,permissions);



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);

    }


    private void deactivateAccount() {

       //final FirebaseUser uid = user;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        if (user !=null){
            progressDialog.setMessage("Deleting account...");
            progressDialog.show();
            try {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this, Main3Activity.class));
                            Toast.makeText(MainActivity.this,"Deactivation was successful",Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();

                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(MainActivity.this,"No user is loggin!!",Toast.LENGTH_LONG).show();

        }


    }
    /*@Override
    protected void onResume() {
        super.onResume();
        if(locationServices.getLocationIsEnable()){
            finish();
            startActivity(getIntent());
            locationServices.setLocationAvailable(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (locationServices.getLocationIsEnable()){
            finish();
            startActivity(getIntent());
            locationServices.setLocationAvailable(false);

        }
    }*/


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){
        Toast.makeText(getApplicationContext(),"Permissions granted",Toast.LENGTH_SHORT).show();
        }
    }

    @AfterPermissionGranted(123)
    public void Main4Activity(){
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this,permission)){
            Log.d(TAG2,"Already has permission");
            Intent intent4 = new Intent(this,PostedImages.class);
            startActivity(intent4);
        }
        else {
            EasyPermissions.requestPermissions(this,"This app requires location and camera permissions to take picture and know where it is posted from",
                    123,permission);
        }

    }
}
