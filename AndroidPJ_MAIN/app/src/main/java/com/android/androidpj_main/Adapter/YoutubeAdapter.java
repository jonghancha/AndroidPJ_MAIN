package com.android.androidpj_main.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Bean.Youtube;
import com.android.androidpj_main.Make_Youtube.YoutubeViewActivity;
import com.android.androidpj_main.R;

import java.util.ArrayList;


public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.MyViewHolder> {

    Context mContext = null;
    int layout = 0;
    ArrayList<Youtube> data = null;
    LayoutInflater inflater = null;

    public YoutubeAdapter(Context mContext, int layout, ArrayList<Youtube> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_makeup, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.name.setText(data.get(position).getYtName());
        holder.url.setText(data.get(position).getYtUrl());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), YoutubeViewActivity.class);
               intent.putExtra("ytUrl", data.get(position).getYtUrl());
               intent.putExtra("ytContent", data.get(position).getYtContent());

               v.getContext().startActivity(intent);
               Toast.makeText(v.getContext(), "영상 재생 시작", Toast.LENGTH_SHORT).show();

           }
       });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView url;


        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_contact);
            url = itemView.findViewById(R.id.ph_number);
        }
    }
}
