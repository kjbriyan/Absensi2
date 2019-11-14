package com.example.absensi.ui.absen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.absensi.R;
import com.example.absensi.Sharedprefs.SharedPreff;
import com.example.absensi.model.checkin.ResponseCheckin;
import com.example.absensi.model.checkout.Responseout;
import com.example.absensi.model.dataUser.ResponseDataUser;
import com.example.absensi.model.ijin.ResponseIjin;
import com.example.absensi.network.Initretrofit;
import com.google.android.gms.maps.model.LatLng;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
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
    String ct, cl, clo, ck, cid, cuse, idlead;

    String alat;
    String blong;
    TextView tgl, name, lokasi;
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

    private double userLat, userLong;
    private Location lastKnownLocation;
    private LatLng userLocation;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_absen, container, false);
        cuse = Prefs.getString(SharedPreff.getId(), "");
        idlead = Prefs.getString(SharedPreff.getIdLeader(), "");

//        dataUser();
        alat = Prefs.getString(SharedPreff.getLatitude(),"");
        blong = Prefs.getString(SharedPreff.getLong(),"");


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd ");
        SimpleDateFormat postjam = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat jam22 = new SimpleDateFormat("HHmm");
        jams = jam22.format(c);
        formattedDate = postjam.format(c);


        in = root.findViewById(R.id.in);
        out = root.findViewById(R.id.out);
        lokasi = root.findViewById(R.id.tv_lokasi);

        name = root.findViewById(R.id.tv_name);
        tgl = root.findViewById(R.id.tgl);
        cekgps();

        button();
        managerCompat = NotificationManagerCompat.from(getActivity());


        name.setText(Prefs.getString(SharedPreff.getName(), ""));
        tgl.setText(formattedDate);
        ct = tgl.getText().toString();


        Toast.makeText(getActivity(), ""+alat+"--"+blong+"--"+ceklat+"--"+ceklong, Toast.LENGTH_SHORT).show();


        return root;
    }

    private void notif() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!"); //Required on Gingerbread and below


        int i = Integer.parseInt(jams);
        int u = 903;
        if (i == u) {
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());
        } else {
            Toast.makeText(getActivity(), "gagal slur", Toast.LENGTH_SHORT).show();

        }
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
//                                cekdatain();
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
//                                cekdataout();
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

//    private void dataUser() {
//
//        Call<ResponseDataUser> call = Initretrofit.getInstance().getDataUser(cuse);
//        call.enqueue(new Callback<ResponseDataUser>() {
//            @Override
//            public void onResponse(Call<ResponseDataUser> call, Response<ResponseDataUser> response) {
//                ResponseDataUser res = response.body();
//                if (response.isSuccessful() && res.getData() != null) {
//                    alat = res.getData().get(0).getLatitude();
//                    blong = res.getData().get(0).getLongitude();
//                    Log.d(TAG, "onResponsedatauser: " + alat + "----" + blong);
////                    setTextlokasi();
//                } else {
//                    Toast.makeText(getActivity(), "fail get data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDataUser> call, Throwable t) {
//                Toast.makeText(getActivity(), "" + t.getCause() + " " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void cekdatain() {
        if (ceklat != null && ceklong != null) {
            ceklokasi();
        } else {
            Toast.makeText(getActivity(), "lokasi hp kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void cekdataout() {
        if (ceklat != null && ceklong != null) {
            ceklokasiout();
        } else {
            Toast.makeText(getActivity(), "lokasi hp kosong", Toast.LENGTH_SHORT).show();
        }
    }


    private void ceklokasi() {



        DecimalFormat df = new DecimalFormat("#.###");

        String ss = ceklat.replace(",", ".");
        String bb = ceklong.replace(",", ".");

        ct = tgl.getText().toString();
        cl = ceklat;
        clo = ceklong;


        Log.d(TAG, "ceklokasi: " + ct + cl + clo + " " + ck + " " + cuse);

        int i = Integer.parseInt(jams);
        int u = 800;
        if (i <= u) {
            cid = "3";
        } else {
            cid = "1";

        }

        if (alat.equals(null)){

            if (ss.equals(alat) && bb.equals(blong)) {
                post();
                Log.d(TAG, "ceklokasi: " + ss + "--" + alat + " " + bb + "--" + blong);
            } else {
                cid = "4";
                //post();
                DialogForm();
                Toast.makeText(getActivity(), "Anda tidak dalam jangkauan" + ss + "--" + alat + " " + bb + "--" + blong, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "ceklokasi: " + ss + "--" + alat + " " + bb + "--" + blong);
                //sendOnChanel();
            }
        }else {
                post();
            Log.d(TAG, "ceklokasi: " + ss + "--" + alat + " " + bb + "--" + blong);
            }


//        if (ceklong != null && ceklat != null) {
//            if (alat == null) {
//                post();
//            } else {
//                if (ss.equals(alat) && bb.equals(blong)) {
//                    post();
//                } else {
//                    cid = "4";
//                    //post();
//                    DialogForm();
//                    Toast.makeText(getActivity(), "Anda tidak dalam jangkauan" + ss + "--" + alat + " " + bb + "--" + blong, Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "ceklokasi: " + ss + "--" + alat + " " + bb + "--" + blong);
//                    //sendOnChanel();
//                }
//            }
//        } else {
//            // getLocation();
//            Toast.makeText(getActivity(), "Your Location Unknow Open your map and Restart the app", Toast.LENGTH_LONG).show();
//        }
    }

    private void ceklokasiout() {
//        cekgps();
        DecimalFormat df = new DecimalFormat("#.###");
        String ss = ceklat.replace(",", ".");
        String bb = ceklong.replace(",", ".");



        ct = tgl.getText().toString();
        cl = ceklat;
        clo = ceklong;


        Log.d(TAG, "cek post: " + ct + cl + clo + ck + cuse);

        int i = Integer.parseInt(jams);
        int u = 1700;
        if (i >= u) {
            cid = "3";
        } else {
            cid = "1";

        }

        if (alat.equals(null)){

            if (ss.equals(alat) && bb.equals(blong)) {
                postout();
                Log.d(TAG, "ceklokasi: " + ss + "--" + alat + " " + bb + "--" + blong);
            } else {
                cid = "4";
                //post();
                DialogForm();
                Toast.makeText(getActivity(), "Anda tidak dalam jangkauan" + ss + "--" + alat + " " + bb + "--" + blong, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "ceklokasi: " + ss + "--" + alat + " " + bb + "--" + blong);
                //sendOnChanel();
            }
        }else {
            postout();
            Log.d(TAG, "ceklokasi: " + ss + "--" + alat + " " + bb + "--" + blong);
        }
//        if (ceklong != null && ceklat != null) {
//            if (alat == null) {
//                postout();
//            } else {
//                if (ss.equals(alat) && bb.equals(blong)) {
//                    postout();
//                } else {
//                    cid = "4";
//                    DialogForm();
//                    Toast.makeText(getActivity(), "Anda tidak dalam jangkauan" + ss + "--" + alat + " " + bb + "--" + blong, Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "ceklokasi out: " + ss + "--" + alat + " " + bb + "--" + blong);
//                    //sendOnChanel();
//                }
//            }
//        } else {
////            getLocation();
//            Toast.makeText(getActivity(), "Your Location Unknow Open your map and Restart the app", Toast.LENGTH_LONG).show();
//        }
    }

    private void setTextlokasi() {
//        cekgps();
        DecimalFormat df = new DecimalFormat("#.###");
        String ss = ceklat.replace(",", ".");
        String bb = ceklong.replace(",", ".");



//        ct = tgl.getText().toString();
//        cl = ceklat;
//        clo = ceklong;
        if (ceklong != null && ceklat != null) {
            if (ss.equals(alat) && bb.equals(blong)) {
                lokasi.setText("Kamu dalam lokasi");
            } else {
                lokasi.setText("Kamu Tidak dalam lokasi");
            }
        } else {
//            getLocation();
            lokasi.setText("Your Location Unknow Open your map and Restart the app");
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
                ijin();

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
//            getLocatio();
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
        Log.d(TAG, "post: " + ct + "---" + cl + "----" + clo);
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

    private void ijin() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        String acc = "0";
        Log.d(TAG, "ijin: " + cuse + "-" + idlead + "-" + ck + "-" + cl + "-" + clo + "-" + ct);
        Call<ResponseIjin> call = Initretrofit.getInstance().ijin(cuse, idlead, ck, acc, cl, clo, ct);
        call.enqueue(new Callback<ResponseIjin>() {
            @Override
            public void onResponse(Call<ResponseIjin> call, Response<ResponseIjin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), "Sukses kirim ijin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Gagal kirim", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseIjin> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail connection " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
//            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

//            if (LocationGps != null) {
//                double lat = LocationGps.getLatitude();
//                double longi = LocationGps.getLongitude();
//
//
////                showLocationTxt.setText(df.format(lat));
//                ceklat = df.format(lat);
//                ceklong = df.format(longi);
//                pd.setVisibility(View.GONE);
//                Log.d(TAG, "getLocation: gps");
//            }
            if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);


//                showLocationTxt.setText(df.format(lat));
                ceklat = df.format(lat);
                ceklong = df.format(longi);
                setTextlokasi();
                pd.setVisibility(View.GONE);
                Log.d(TAG, "getLocation: network");
            }
//            else if (LocationPassive != null) {
//                double lat = LocationPassive.getLatitude();
//                double longi = LocationPassive.getLongitude();
//
//                latitude = String.valueOf(lat);
//                longitude = String.valueOf(longi);
//
////                showLocationTxt.setText(df.format(lat));
//                ceklat = df.format(lat);
//                ceklong = df.format(longi);
//                pd.setVisibility(View.GONE);
//                Log.d(TAG, "getLocation: passive");
//            }
            else {
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

    void getLocatio() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Get Location...", "Please wait...", false, false);
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, this);
            loading.dismiss();
        } catch (SecurityException e) {
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
