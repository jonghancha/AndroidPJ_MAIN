package com.android.androidpj_main.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Bean.Youtube;
import com.android.androidpj_main.Make_Youtube.YoutubeViewActivity;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

// 지은 추가 21.01.07 ***************************
public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.MyViewHolder> {

    Context mContext = null;
    int layout = 0;
    ArrayList<Youtube> data = null;
    LayoutInflater inflater = null;
    String urlAddr;

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

        urlAddr = "http://" + ShareVar.macIP + ":8080/youtubeImg/";  // youtubeImg 파일
        urlAddr = urlAddr + data.get(position).getYtImg(); // 경로에 이미지 이름 추가

        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 100%;\"" +
                "</center></body>" +
                "</html>";
        holder.web_youtube.loadData(htmlData,"text/html", "UTF-8");


        holder.name.setText(data.get(position).getYtName());
//        holder.url.setText(data.get(position).getYtUrl());

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
//        TextView url;
        WebView web_youtube;

        public MyViewHolder(View itemView) {
            super(itemView);
            web_youtube = itemView.findViewById(R.id.web_youtube);
            name = itemView.findViewById(R.id.name_contact);
//            url = itemView.findViewById(R.id.ph_number);

            // WebView 세팅
            // Web Setting
            WebSettings webSettings = web_youtube.getSettings();
            webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
            webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
            web_youtube.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투명으로 전환
        }
    }
}
