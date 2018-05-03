package com.example.fatin.foodbasket;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }*/

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int permissionCode = 1;

    private boolean locationGranted = false;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getPermission();


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"map is ready");
        map = googleMap;
    }
    private void getCurrentLocation(){ //Get user's current location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }
    private void startMap(){
        Log.d(TAG,"starting map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }
    private void getPermission(){
        Log.d(TAG,"getting location permission");
        String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                                 Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)
                ==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)
                    ==PackageManager.PERMISSION_GRANTED){
                locationGranted=true;
                startMap();

            }
            else {
                ActivityCompat.requestPermissions(this,permissions,permissionCode);
            }
        }
        else {
            ActivityCompat.requestPermissions(this,permissions,permissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"called");
        locationGranted=false;
        switch (requestCode){
            case permissionCode:{
                if (grantResults.length>0){
                    for (int i =0;i<grantResults.length;i++){
                        if (grantResults[i] !=PackageManager.PERMISSION_GRANTED){
                            locationGranted = false;
                            return;
                        }else {
                            Log.d(TAG,"permission granted");
                            locationGranted = true;
                            startMap();
                        }
                    }
                    //start map here
                }
            }
        }
    }


}
