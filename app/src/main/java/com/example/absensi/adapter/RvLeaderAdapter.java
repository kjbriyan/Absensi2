package com.example.absensi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.Leader.FormAccActivity;
import com.example.absensi.Leader.LeaderActivity;
import com.example.absensi.R;
import com.example.absensi.model.dataijin.DataItem;

import java.util.List;

public class RvLeaderAdapter extends RecyclerView.Adapter<RvLeaderAdapter.View_holder>  {
    Context mContext;
    List<DataItem> itemList;
    public RvLeaderAdapter(Context mContext, List<DataItem> itemList) {

        this.mContext = mContext;
        this.itemList = itemList;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_ijin, parent, false);

        final View_holder holder = new View_holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_holder holder, final int position) {

        holder.name.setText(itemList.get(position).getName());
        holder.ket.setText(itemList.get(position).getKeterangan());
        holder.tgl.setText(itemList.get(position).getDateCreated());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FormAccActivity.class);
                i.putExtra("name",itemList.get(position).getName());
                i.putExtra("ket",itemList.get(position).getKeterangan());
                i.putExtra("id",itemList.get(position).getIdReason());
                i.putExtra("tgl",itemList.get(position).getDateCreated());
                view.getContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return  itemList.size();
    }

    public class View_holder extends RecyclerView.ViewHolder {
        TextView name,ket,tgl;
        LinearLayout cv ;
        public View_holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            ket= itemView.findViewById(R.id.tv_keterangan);
            tgl= itemView.findViewById(R.id.tv_tgl);
            cv= itemView.findViewById(R.id.linearLayout);

        }
    }
}
