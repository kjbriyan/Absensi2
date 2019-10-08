package com.example.absensi.ui.absen;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.absensi.Activity.INActivity;
import com.example.absensi.MainActivity;
import com.example.absensi.R;
import com.example.absensi.Sharedprefs.SharedPreff;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    LocationManager locationManager;
    String latitude, longitude;
    String ceklat,ceklong;
    EditText ket;
    TextView tgl,name;

    private static final int REQUEST_LOCATION = 1;

    Button in,out;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_absen, container, false);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);


        in = root.findViewById(R.id.in);
        out = root.findViewById(R.id.out);
        ket = root.findViewById(R.id.et_keterangan);
        name = root.findViewById(R.id.tv_name);
        tgl = root.findViewById(R.id.tgl);


        name.setText(Prefs.getString(SharedPreff.getName(),""));
        tgl.setText(formattedDate);
        ket.setVisibility(View.INVISIBLE);

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder( getContext() )
                        .setTitle( "Check in" )
                        .setMessage( "Pastikan anda ada dalam lokasi kantor anda" )
                        .setPositiveButton( "Check In", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ceklokasi();
                            }
                        } ).setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } ).create().show();
//                Intent i = new Intent(getActivity(), INActivity.class);
//                getActivity().startActivity(i);
                cekgps();
                Log.d(TAG, "onClick: "+ceklat+ceklong);
            }
        });
        return root;
    }

    private void ceklokasi(){
        String alat="-8,216";
        String blong="114,369";

        if (ceklat.equals(alat) && ceklong.equals(blong)){
            Toast.makeText(getActivity(), "good", Toast.LENGTH_SHORT).show();
        }else{
            ket.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Bad", Toast.LENGTH_SHORT).show();
        }
    }

    private void cekgps(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps

            OnGPS();
        } else {
            //GPS is already On then

            getLocation();
        }
    }
    private void getLocation() {
        DecimalFormat df = new DecimalFormat("#.###");
        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
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

//                showLocationTxt.setText(df.format(lat));
                ceklat=df.format(lat);
                ceklong=df.format(longi);
            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);


//                showLocationTxt.setText(df.format(lat));
                ceklat=df.format(lat);
                ceklong=df.format(longi);
            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

//                showLocationTxt.setText(df.format(lat));
                ceklat=df.format(lat);
                ceklong=df.format(longi);
            } else {
                Toast.makeText(getActivity(), "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }



    }

    private void OnGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
