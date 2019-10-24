package com.example.absensi.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.absensi.R;
import com.example.absensi.Sharedprefs.SharedPreff;
import com.example.absensi.adapter.RvLeaderAdapter;
import com.example.absensi.model.dataijin.DataItem;
import com.example.absensi.model.dataijin.ResponseijinLead;
import com.example.absensi.model.ijin.ResponseIjin;
import com.example.absensi.network.Initretrofit;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class IjinFragment extends Fragment {

    String id;

    RvLeaderAdapter adapter;
    RecyclerView rv;
    public IjinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ijin, container, false);
        rv= v.findViewById(R.id.rv_ijin);
        LinearLayoutManager lln = new LinearLayoutManager(getContext());
        lln.setReverseLayout(true);
        rv.setLayoutManager(lln);
        rv.setHasFixedSize(true);
        id= Prefs.getString(SharedPreff.getId(),"");
        getData();

        return v;
    }
    private void getData(){

        Call<ResponseijinLead> call= Initretrofit.getInstance().getDataIjin(id);
        call.enqueue(new Callback<ResponseijinLead>() {
            @Override
            public void onResponse(Call<ResponseijinLead> call, Response<ResponseijinLead> response) {
                ResponseijinLead res =response.body();
                if (res.getData()!=null){
                    List<DataItem> data = res.getData();
                    adapter= new RvLeaderAdapter(getActivity(),data);
                    rv.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(), "Fail get Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseijinLead> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
