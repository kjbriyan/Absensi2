package com.example.absensi.Leader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absensi.R;
import com.example.absensi.model.updatestatusijin.ResponseStatusIjin;
import com.example.absensi.network.Initretrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAccActivity extends AppCompatActivity {

    TextView name,ketera,tgl;
    Button acc,tolak;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_persetujuan);
        name = findViewById(R.id.tv_name);
        ketera = findViewById(R.id.tv_keterangan);
        tolak = findViewById(R.id.btn_tolak);
        acc = findViewById(R.id.btn_stuju);
        tgl=findViewById(R.id.tv_tgl);
        name.setText(getIntent().getExtras().getString("name"));
        ketera.setText(getIntent().getExtras().getString("ket"));
        tgl.setText(getIntent().getExtras().getString("tgl"));
        btn();

    }
    private void btn(){
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "1";
                updatestatus();
            }
        });

        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="2";
                updatestatus();
            }
        });
    }
    private void updatestatus(){
        final ProgressDialog loading = ProgressDialog.show(this, "Loading...", "Please wait...", false, false);
        String idijin= getIntent().getExtras().getString("id");
        Log.d("hiyaa", "updatestatus: "+idijin+"//"+status);
        Call<ResponseStatusIjin> call = Initretrofit.getInstance().updatestatus(idijin,status);
        call.enqueue(new Callback<ResponseStatusIjin>() {
            @Override
            public void onResponse(Call<ResponseStatusIjin> call, Response<ResponseStatusIjin> response) {
                if (response.isSuccessful()&& response.body() != null){
                    Toast.makeText(FormAccActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(FormAccActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseStatusIjin> call, Throwable t) {
                Toast.makeText(FormAccActivity.this, ""+t.getCause()+" "+t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
