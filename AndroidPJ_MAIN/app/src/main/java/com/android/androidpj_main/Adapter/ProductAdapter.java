package com.android.androidpj_main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Activity.ProductViewActivity;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    final static String TAG = "ProductAdapter";

    // 지은 추가 21.01.11 *************************************

    Context mContext = null;
    int layout = 0;
    ArrayList<Product> data = null;
    LayoutInflater inflater = null;
    String urlAddr;

    public ProductAdapter(Context mContext, int layout, ArrayList<Product> data){
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
        v = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false);
        ProductHolder productHolder = new ProductHolder(v);
        return productHolder;
    }


    // 추가 될때 생명주기
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

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
        holder.web_product.loadData(htmlData,"text/html", "UTF-8");

        // 상품 브랜드
        holder.product_brand.setText("[ "+data.get(position).getPrdBrand()+ " ]");
        // 상품 이름
        holder.product_name.setText(data.get(position).getPrdName());
        // 상품 가격
        Log.v(TAG, String.valueOf(data.get(position).getPrdPrice()) );
        holder.product_price.setText(String.valueOf(data.get(position).getPrdPrice())+" 원");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ProductViewActivity.class);
                intent.putExtra("prdName", data.get(position).getPrdName());
                intent.putExtra("prdNo", data.get(position).getPrdNo());
                intent.putExtra("prdPrice", data.get(position).getPrdPrice());

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

        // item_product.xml 선언
        WebView web_product;
        protected TextView product_brand;
        protected TextView product_name;
        protected TextView product_price;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            web_product = itemView.findViewById(R.id.web_product);
            product_brand = itemView.findViewById(R.id.product_brand);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);

            // WebView 세팅
            // Web Setting
            WebSettings webSettings = web_product.getSettings();
            webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
            webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
            web_product.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투명으로 전환
        }
    }
}
