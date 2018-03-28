package com.example.fatin.foodbasket;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsStatusCodes;


public class AppLocationServices extends Service implements LocationListener {
    private final Context _context;
    private boolean isGpsEnable =false;
    private boolean isNetworkEnable =false;
    private boolean isLocationAvailable = false;

   // GoogleApiClient googleApiClient;

    LocationManager locationManager;


  //  PendingResult<LocationSettingsResult> pendingResult;


    Location _location =null;
    private double latitude =0.0;
    private double longitude= 0.0;

    private static  final long MIN_UPDATE_LOCATION =5;
    private static final long MIN_UPDATE_TIME = 1000*60*1;

    private LocationManager _locationMgr;


    public AppLocationServices(Context _context){
        this._context=_context;
        initLocation();

    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Location getLocation() {
        return _location;
    }

    public double getLongitude() {
        return longitude;
    }
    public void displayLocationSetting(){
        //final AlertDialog.Builder setting=null;
        try {
            final AlertDialog.Builder setting = new AlertDialog.Builder(_context);
            setting.setTitle("Enable Location Wizard");
           // setting.setMessage("Enable location services");
            setting.setCancelable(false);
            setting.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    _context.startActivity(intent);
                }
            });
            setting.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            final AlertDialog alert= setting.create();
            alert.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public boolean getLocationIsEnable(){
        return this.isLocationAvailable;
    }
    public void setLocationAvailable(boolean f){
        isLocationAvailable=f;
    }
    public Location initLocation(){

        try {
            _locationMgr =(LocationManager) _context.getSystemService(LOCATION_SERVICE);
            isGpsEnable = _locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnable = _locationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isNetworkEnable && isGpsEnable){
                this.isLocationAvailable =true;
                if (isNetworkEnable){
                    if (ActivityCompat.checkSelfPermission((Activity)_context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission((Activity)_context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        return null;

                    }
                    _locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_LOCATION, this);
                    if (_locationMgr != null){
                        _location =_locationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (_location != null){
                            this.setLatitude(_location.getLatitude());
                            this.setLongitude(_location.getLongitude());
                        }
                    }
                }
                if (isGpsEnable){
                    if (_location ==null){
                        _locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_LOCATION, this);
                        if (_locationMgr != null){
                            _location = _locationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (_location != null){
                                this.setLatitude(_location.getLatitude());
                                this.setLongitude(_location.getLongitude());
                            }
                        }
                    }
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return _location;

    }

    public void disableGPS(){
        if (_locationMgr != null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;

            }

        }
        _locationMgr.removeUpdates(AppLocationServices.this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}


