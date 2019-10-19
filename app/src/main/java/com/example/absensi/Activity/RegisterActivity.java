package com.example.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.absensi.R;

public class RegisterActivity extends AppCompatActivity {

    EditText us, pass;
    Button btn;
    String no,imei;
    TextView regis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        deklarasi();


    }
    private void deklarasi(){
        us = findViewById(R.id.et_usname);
        btn = findViewById(R.id.btnregis);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regis();
            }
        });
    }
    private void regis(){

    }
}
