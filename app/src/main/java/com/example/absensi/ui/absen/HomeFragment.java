package com.example.absensi.ui.absen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.absensi.R;
import com.example.absensi.Sharedprefs.SharedPreff;
import com.example.absensi.model.Login.Data;
import com.example.absensi.model.checkin.ResponseCheckin;
import com.example.absensi.model.checkout.Responseout;
import com.example.absensi.model.dataUser.DataItem;
import com.example.absensi.model.dataUser.ResponseDataUser;
import com.example.absensi.network.Initretrofit;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements LocationListener {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    LocationManager locationManager;
    Location location;
    String latitude, longitude;
    String ceklat, ceklong;
    String ct, cl, clo, ck, cid, cuse;

    String alat ;
    String blong ;
    String alat2 ;
    String blong2;
    String alata ;
    String blonga ;
    String alat2a ;
    String blong2a ;

    TextView tgl, name;
    String formattedDate;
    String jams;
    EditText txt_ket;

    private static final int LOCATION_PERMISSION_ID = 1001;


    private NotificationManagerCompat managerCompat;
    private static final int REQUEST_LOCATION = 1;

    Button in, out;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_absen, container, false);

        cuse = Prefs.getString(SharedPreff.getId(), "");
        dataUser();
        cekgps();
//        getLocatio();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd ");
        SimpleDateFormat postjam = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat jam22 = new SimpleDateFormat("HHmm");
        jams = jam22.format(c);
        formattedDate = postjam.format(c);


        in = root.findViewById(R.id.in);
        out = root.findViewById(R.id.out);

        name = root.findViewById(R.id.tv_name);
        tgl = root.findViewById(R.id.tgl);

        button();
        managerCompat = NotificationManagerCompat.from(getActivity());


        name.setText(Prefs.getString(SharedPreff.getName(), ""));
        tgl.setText(formattedDate);


        return root;
    }


    private void button() {
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(getContext())
                        .setTitle("Check in")
                        .setMessage("Pastikan anda ada dalam lokasi kantor anda")
                        .setPositiveButton("Check In", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ceklokasi();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
//                Intent i = new Intent(getActivity(), INActivity.class);
//                getActivity().startActivity(i);

                Log.d(TAG, "onClick: " + ceklat + ceklong);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(getContext())
                        .setTitle("Check out")
                        .setMessage("Pastikan anda ada dalam lokasi kantor anda")
                        .setPositiveButton("Check out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ceklokasiout();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
//                Intent i = new Intent(getActivity(), INActivity.class);
//                getActivity().startActivity(i);

                Log.d(TAG, "onClick: " + ceklat + ceklong);
            }
        });
    }

    private void dataUser(){

        Call<ResponseDataUser> call = Initretrofit.getInstance().getDataUser(cuse);
        call.enqueue(new Callback<ResponseDataUser>() {
            @Override
            public void onResponse(Call<ResponseDataUser> call, Response<ResponseDataUser> response) {
                ResponseDataUser res = response.body();
                if (response.isSuccessful() && res.getData() != null){
                     alat= res.getData().get(0).getLatitude();
                     blong=res.getData().get(0).getLongitude();
                    Log.d(TAG, "onResponsedatauser: "+alat+"----"+blong);
                }else {
                    Toast.makeText(getActivity(), "fail get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataUser> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getCause()+" "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ceklokasi() {
        cekgps();
        DecimalFormat df = new DecimalFormat("#.###");
        String ss = ceklat.replace(",",".");
        String bb = ceklong.replace(",",".");
        double a = Double.parseDouble(alat);
        double b = Double.parseDouble(blong);
        double var= 0.001;
        double alatmin =a+var;
        double blongmin=b-var;
        double alatplus=var-a;
        double blongplus=b+var;
        String y = df.format(alatplus);
        String f = y.replace(",",".");
        Log.d(TAG, "pengurangan: "+a+"++"+var+"---"+alatmin+"ewww"+b+"wew"+blongmin);
        Log.d(TAG, "penambahan: "+f+"   "+blongplus);


        ct = tgl.getText().toString();
        cl = ceklat;
        clo = ceklong;




        Log.d(TAG, "ceklokasi: " + ct + cl + clo + ck + cuse);

        int i = Integer.parseInt(jams);
        int u = 800;
        if (i <= u) {
            cid = "3";
        } else {
            cid = "1";

        }

        if (ceklong != null && ceklat != null) {
            if (ss.equals(alat) && bb.equals(blong)) {
                post();
            }else if (ss.equals(alat)&&bb.equals(blongmin)){
                post();
                Log.d(TAG, "ceklokasi: long di min");
            }else if (ss.equals(alat)&&bb.equals(blongplus)){
                post();
                Log.d(TAG, "ceklokasi: ini long di plus");
            }else if (ss.equals(alatmin)&&bb.equals(blong)){
                post();
                Log.d(TAG, "ceklokasi: alatmin blong");
            }else if (ss.equals(alatmin)&&bb.equals(blongmin)){
                post();
                Log.d(TAG, "ceklokasi: alatmin blong min");
            }else if (ss.equals(alatmin)&&bb.equals(blongplus)){
                post();
                Log.d(TAG, "ceklokasi: alatmin blong plus");
            }else if (ss.equals(alatplus)&&bb.equals(blong)){
                post();
                Log.d(TAG, "ceklokasi: alat plus blong");
            }else if (ss.equals(alatplus)&&bb.equals(blongmin)){
                post();
                Log.d(TAG, "ceklokasi: alatplus blong min");
            }else if (ss.equals(alatplus)&&bb.equals(blongplus)){
                post();
                Log.d(TAG, "ceklokasi: alatplus blong plus");
            }else {
                cid = "4";
                //post();
                DialogForm();

                Toast.makeText(getActivity(), "Anda tidak dalam jangkauan" + ceklat+"--"+alat + " " + ceklong+"--"+blong, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "ceklokasi: "+ ceklat+"--"+alat + " " + ceklong+"--"+blong);
                //sendOnChanel();
            }
        } else {
            getLocation();
            Toast.makeText(getActivity(), "Your Location Unknow Open your map and Restart the app", Toast.LENGTH_LONG).show();
        }
    }

    private void ceklokasiout() {
        cekgps();
        DecimalFormat df = new DecimalFormat("#.###");
        String ss = ceklat.replace(",",".");
        String bb = ceklong.replace(",",".");
        double a = Double.parseDouble(alat);
        double b = Double.parseDouble(blong);
        double var= 0.001;
        double alatmin =a+var;
        double blongmin=b-var;
        double alatplus=var-a;
        double blongplus=b+var;
        String y = df.format(alatplus);
        String f = y.replace(",",".");
        Log.d(TAG, "pengurangan: "+a+"++"+var+"---"+alatmin+"ewww"+b+"wew"+blongmin);
        Log.d(TAG, "penambahan: "+f+"   "+blongplus);


        ct = tgl.getText().toString();
        cl = ceklat;
        clo = ceklong;




        Log.d(TAG, "ceklokasi: " + ct + cl + clo + ck + cuse);

        int i = Integer.parseInt(jams);
        int u = 1700;
        if (i >= u) {
            cid = "3";
        } else {
            cid = "1";

        }

        if (ceklong != null && ceklat != null) {
            if (ss.equals(alat) && bb.equals(blong)) {
                postout();
                Toast.makeText(getActivity(), ss+"||"+alat+"||"+bb+"||"+blong, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alat)&&bb.equals(blongmin)){
                postout();
                Log.d(TAG, "ceklokasi: long di min");
                Toast.makeText(getActivity(), ss+"||"+alat+"||"+bb+"||"+blongmin, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alat)&&bb.equals(blongplus)){
                postout();
                Log.d(TAG, "ceklokasi: ini long di plus");
                Toast.makeText(getActivity(), ss+"||"+alat+"||"+bb+"||"+blongplus, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alatmin)&&bb.equals(blong)){
                postout();
                Log.d(TAG, "ceklokasi: alatmin blong");
                Toast.makeText(getActivity(), ss+"||"+alatmin+"||"+bb+"||"+blong, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alatmin)&&bb.equals(blongmin)){
                postout();
                Log.d(TAG, "ceklokasi: alatmin blong min");
                Toast.makeText(getActivity(), ss+"||"+alatmin+"||"+bb+"||"+blongmin, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alatmin)&&bb.equals(blongplus)){
                postout();
                Log.d(TAG, "ceklokasi: alatmin blong plus");
                Toast.makeText(getActivity(), ss+"||"+alatmin+"||"+bb+"||"+blongplus, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alatplus)&&bb.equals(blong)){
                postout();
                Log.d(TAG, "ceklokasi: alat plus blong");
                Toast.makeText(getActivity(), ss+"||"+alatplus+"||"+bb+"||"+blong, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alatplus)&&bb.equals(blongmin)){
                postout();
                Log.d(TAG, "ceklokasi: alatplus blong min");
                Toast.makeText(getActivity(), ss+"||"+alatplus+"||"+bb+"||"+blongmin, Toast.LENGTH_SHORT).show();
            }else if (ss.equals(alatplus)&&bb.equals(blongplus)){
                postout();
                Log.d(TAG, "ceklokasi: alatplus blong plus");
                Toast.makeText(getActivity(), ss+"||"+alatplus+"||"+bb+"||"+blongplus, Toast.LENGTH_SHORT).show();
            }else {
                cid = "4";
                DialogFormout();
                Toast.makeText(getActivity(), "Anda tidak dalam jangkauan" + ss+"--"+ " " + bb+"--", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "ceklokasi: "+ceklat+"--"+ceklong+"--");
                //sendOnChanel();
            }
        } else {
            getLocation();
            Toast.makeText(getActivity(), "Your Location Unknow Open your map and Restart the app", Toast.LENGTH_LONG).show();
        }
    }


    private void DialogForm() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_ijin, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form ijin diluar area kantor");
        txt_ket = (EditText) dialogView.findViewById(R.id.et_keterangan);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ck = txt_ket.getText().toString();
                post();

                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void DialogFormout() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_ijin, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form ijin diluar area kantor");
        txt_ket = (EditText) dialogView.findViewById(R.id.et_keterangan);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ck = txt_ket.getText().toString();
                postout();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void cekgps() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps

            OnGPS();
        } else {
            //GPS is already On then
//                getLocatio();
            getLocation();
        }
    }

    private void postout() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        Log.d(TAG, "post: " + ct + "--" + cid + "--" + cuse);
        Call<Responseout> call = Initretrofit.getInstance().Checkout(ct, cl, clo, cid, ck, cuse);
        call.enqueue(new Callback<Responseout>() {
            @Override
            public void onResponse(Call<Responseout> call, Response<Responseout> response) {
                Responseout res = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), "Sukses check out", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<Responseout> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage() + t.getCause(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    private void post() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        Log.d(TAG, "post: " + ct);
        Call<ResponseCheckin> call = Initretrofit.getInstance().CheckIn(ct, cl, clo, cid, ck, cuse);
        call.enqueue(new Callback<ResponseCheckin>() {
            @Override
            public void onResponse(Call<ResponseCheckin> call, Response<ResponseCheckin> response) {
                ResponseCheckin res = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), "Sukses check in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseCheckin> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage() + t.getCause(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    private void getLocation() {
        ProgressBar pd = new ProgressBar(getActivity());

        pd.setVisibility(View.VISIBLE);
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



//                showLocationTxt.setText(df.format(lat));
                ceklat = df.format(lat);
                ceklong = df.format(longi);
                pd.setVisibility(View.GONE);
                Log.d(TAG, "getLocation: gps");
            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);


//                showLocationTxt.setText(df.format(lat));
                ceklat = df.format(lat);
                ceklong = df.format(longi);
                pd.setVisibility(View.GONE);
                Log.d(TAG, "getLocation: network");
            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

//                showLocationTxt.setText(df.format(lat));
                ceklat = df.format(lat);
                ceklong = df.format(longi);
                pd.setVisibility(View.GONE);
                Log.d(TAG, "getLocation: passive");
            } else {
                //Toast.makeText(getActivity(), "Can't Get Your Location", Toast.LENGTH_SHORT).show();
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

    void getLocatio() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Get Location...", "Please wait...", false, false);
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, this);

            loading.dismiss();
        }
        catch(SecurityException e) {
            e.printStackTrace();
            loading.dismiss();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        DecimalFormat df = new DecimalFormat("#.###");
        double lat = location.getLatitude();
        double longi = location.getLongitude();


        latitude = String.valueOf(lat);
        longitude = String.valueOf(longi);


//                showLocationTxt.setText(df.format(lat));
        ceklat = df.format(lat);
        ceklong = df.format(longi);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
}
