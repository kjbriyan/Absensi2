package com.example.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.absensi.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class INActivity extends AppCompatActivity {

    TextView name,tgl;
    TextClock tc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);


        name= findViewById(R.id.tv_name);
        tgl = findViewById(R.id.tgl);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateandTime = sdf.format(new Date());
        tgl.setText(currentDateandTime);


    }
}
