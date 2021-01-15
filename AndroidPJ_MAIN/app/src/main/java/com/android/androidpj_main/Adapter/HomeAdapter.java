package com.android.androidpj_main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Activity.ProductViewActivity;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ProductHolder> {

    final static String TAG = "HomeAdapter";

    ///////////////////////////////////////////////////////////////////////////
    //
    // 21.01.13 세미 생성
    //
    ///////////////////////////////////////////////////////////////////////////

    Context mContext = null;
    int layout = 0;
    ArrayList<Product> data = null;
    LayoutInflater inflater = null;
    String urlAddr;

    public HomeAdapter(Context mContext, int layout, ArrayList<Product> data){
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // 리스트뷰 메뉴가 처음 생성될 때 생명주기
    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_home, parent, false);
        ProductHolder productHolder = new ProductHolder(v);
        return productHolder;
    }


    // 추가 될때 생명주기
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {


        // 상품 이미지
        urlAddr = "http://" + ShareVar.macIP + ":8080/Images/";  // Images 파일
        urlAddr = urlAddr + data.get(position).getPrdFilename(); // 경로에 이미지 이름 추가

        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 100%;\"" +
                "</center></body>" +
                "</html>";
        holder.wv_likeimg.loadData(htmlData,"text/html", "UTF-8");
        // 상품 브랜드
        holder.tv_likebrand.setText(" [ " + data.get(position).getPrdBrand() + " ] ");
        // 상품 이름
        holder.tv_likename.setText(data.get(position).getPrdName());
        // 상품 가격
        holder.tv_likeprice.setText(String.valueOf(data.get(position).getPrdPrice()));

        int prdNo = data.get(position).getPrdNo();


        ///////////////////////////////////////////////////////////////////////////
        //
        //  21.01.09 세미
        //  리스트 클릭 시 데이터 보내주기
        //
        ///////////////////////////////////////////////////////////////////////////

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ProductViewActivity.class);
                intent.putExtra("prdNo", data.get(position).getPrdNo());
                intent.putExtra("prdPrice", data.get(position).getPrdPrice());
                intent.putExtra("prdName", data.get(position).getPrdName());

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    // ProductHolder
    public class ProductHolder extends RecyclerView.ViewHolder {

        // custom_like.xml 선언
        protected WebView wv_likeimg;
        protected TextView tv_likename;
        protected TextView tv_likeprice;
        protected TextView tv_likebrand;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            wv_likeimg = itemView.findViewById(R.id.home_web_product);
            tv_likename = itemView.findViewById(R.id.home_product_name);
            tv_likeprice = itemView.findViewById(R.id.home_product_price);
            tv_likebrand = itemView.findViewById(R.id.home_product_brand);

            // WebView 세팅
            // Web Setting
            WebSettings webSettings = wv_likeimg.getSettings();
            webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
            webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
            wv_likeimg.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투

        }
    }

}//-----------
