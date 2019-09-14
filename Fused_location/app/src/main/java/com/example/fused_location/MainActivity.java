package com.example.fused_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView LatitudeVal;
    private TextView LongitudeVal;
    private static final int  REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private LocationRequest locationRequest;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create location request
        startLocationRequest();

        LatitudeVal =  findViewById(R.id.latitudeVal);
        LongitudeVal = findViewById(R.id.longitudeVal);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        Button locationButton = findViewById(R.id.currentLocation);
        final TextView counter = findViewById(R.id.counter);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    getLastLocation();
                }
                counter.setText("Times Clicked:" +count);
            }
        });
    }
    // check permissions
//    @Override
//    public void onStart(){
//        super.onStart();
//
//        if(!checkPermission()){
//            requestPermission();
//        }
//        else {
//            getLastLocation();
//        }
//    }

    private void requestPermission(){
        startLocationRequest();
    }
    private void startLocationRequest(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private boolean checkPermission(){
        // create variable and check to see if the permission is granted for fine location
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        // return t/f if the state is equal to granted
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void getLastLocation(){
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                           LatitudeVal.setText(Double.toString(location.getLatitude()));
                           LongitudeVal.setText(Double.toString(location.getLongitude()));
                        }
                    }
                });
    }

    // create location request with default parameters
    protected void createLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    // check current location
    private void buildLocationSetting(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        }

}
