package com.example.absensi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absensi.Activity.RegisterActivity;
import com.example.absensi.Leader.LeaderActivity;
import com.example.absensi.Sharedprefs.SharedPreff;
import com.example.absensi.Utils.Move;
import com.example.absensi.model.Login.Data;
import com.example.absensi.model.Login.ResponseLogin;
import com.example.absensi.network.Initretrofit;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText us, pass;
    Button btn;
    String no,imei;

    ResponseLogin login;
    public static final String TAG = "Login";

    private int Storage_Permission_Code = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startinit();
    }

    private void startinit() {





        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


        if (Prefs.getString(SharedPreff.getIdPosition(), null) == null) {
            initUI();
            return;
        }else if (Prefs.getString(SharedPreff.getIdPosition(),"").equals("1")){
            Move.move(this, LeaderActivity.class);
            finish();
        }else if (Prefs.getString(SharedPreff.getIdPosition(),"").equals("2")){
            Move.move(this, LeaderActivity.class);
            finish();
        }else{
            Move.move(this, NavDrawer.class);
            finish();
        }

    }
    private void initUI(){
        btn = findViewById(R.id.btnmasuk);
        us = findViewById(R.id.et_usname);
        ActivityCompat.requestPermissions(Login.this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                1);
        Permissions perm = new Permissions(this);

        perm.loadIMEI();

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"MissingPermission", "HardwareIds"})
            @Override
            public void onClick(View view) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                imei = tm.getDeviceId();
                no = us.getText().toString();
                Log.d(TAG, "onClick:"+imei);
                Log.d(TAG, "onClick: "+no);

                    checkLogin();

            }
        });
    }

    private void checkLogin(){

        final ProgressDialog loading = ProgressDialog.show(this, "Loading...", "Please wait...", false, false);
        Call<ResponseLogin> call = Initretrofit.getInstance().getUser(no,imei);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                login = response.body();

                if (response.body() != null) {
                    Prefs.clear();
                    setPrefference(response.body().getData());
                    Toast.makeText(Login.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(login.getData().getIdPosition().equals("1")){
                        Move.move(Login.this, LeaderActivity.class);
                        finish();
                    }else if (login.getData().getIdPosition().equals("2")){
                        Move.move(Login.this, LeaderActivity.class);
                        finish();
                    }else{
                        Move.move(Login.this, NavDrawer.class);
                        finish();
                    }

                    loading.dismiss();

                }else {
                    Toast.makeText(Login.this, "Gagal Login Mungkin Nomor hp salah atau hp anda tidak sesuai user yang anda gunakan", Toast.LENGTH_SHORT).show();
                   loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPrefference(Data data) {
        Prefs.putString(SharedPreff.getId(),data.getId());
        Prefs.putString(SharedPreff.getName(),data.getName());
        Prefs.putString(SharedPreff.getNoHp(),data.getNoHp());
        Prefs.putString(SharedPreff.getIdPosition(),data.getIdPosition());
        Prefs.putString(SharedPreff.getIdLeader(),data.getIdLeader());
    }

}
