package com.example.absensi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;

    Button getlocationBtn,cek;
    TextView showLocationTxt,ini,itu;

    LocationManager locationManager;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add permission

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        showLocationTxt = findViewById(R.id.show_location);
        getlocationBtn = findViewById(R.id.getLocation);
        ini = findViewById(R.id.tv_lat);
        itu = findViewById(R.id.tv_lat2);
        cek = findViewById(R.id.btncek);

        ini.setText("114");
        itu.setText("114");

        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat("#");
                String a= String.valueOf(showLocationTxt.getText());
                String b = String.valueOf(itu.getText());
                if (a.equals(b)){
                    Toast.makeText(MainActivity.this, "ini sama", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "gak sama", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getlocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps

                    OnGPS();
                } else {
                    //GPS is already On then

                    getLocation();
                }
            }
        });

    }

    private void getLocation() {
        DecimalFormat df = new DecimalFormat("#.###");
        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps != null) {
                double lat = LocationGps.getLatitude();
                double longi = LocationGps.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                showLocationTxt.setText(df.format(lat));
            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);


                showLocationTxt.setText(df.format(lat));
            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                showLocationTxt.setText(df.format(lat));
            } else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }



    }

    private void OnGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
