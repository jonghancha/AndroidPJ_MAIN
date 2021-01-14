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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Activity.HoneyTipActivity;
import com.android.androidpj_main.Activity.ProductViewActivity;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.Bean.Tip;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ProductHolder> {

    final static String TAG = "TipAdapter";

    // 지은 추가 21.01.13 *************************************

    Context mContext = null;
    int layout = 0;
    ArrayList<Tip> data = null;
    LayoutInflater inflater = null;
    String urlAddr;

    public TipAdapter(Context mContext, int layout, ArrayList<Tip> data){
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
        v = LayoutInflater.from(mContext).inflate(R.layout.item_tip, parent, false);
        ProductHolder productHolder = new ProductHolder(v);
        return productHolder;
    }


    // 추가 될때 생명주기
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        urlAddr = "http://" + ShareVar.macIP + ":8080/Images/";  // Images 파일
        urlAddr = urlAddr + data.get(position).getTipImg(); // 경로에 이미지 이름 추가

        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 100%;\"" +
                "</center></body>" +
                "</html>";
        holder.tipImg.loadData(htmlData,"text/html", "UTF-8");


        // 팁 제목
        holder.tipTitle.setText(data.get(position).getTipTitle());

        // 제목 배경 설정 ....
        holder.tipTitle.setBackground(mContext.getResources().getDrawable(R.drawable.tip_title));

        // 팁 내용
        holder.tipContent.setText(data.get(position).getTipContent());
        //
        holder.tipCo.setBackground(mContext.getResources().getDrawable(R.drawable.tip_content));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    // ProductHolder
    public class ProductHolder extends RecyclerView.ViewHolder {

        // item_tip.xml 선언
        protected TextView tipTitle;
        protected TextView tipContent;

        WebView tipImg;

        LinearLayout tipCo;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            tipTitle = itemView.findViewById(R.id.tipTitle);
            tipContent = itemView.findViewById(R.id.tipContent);
            tipImg = itemView.findViewById(R.id.tipImg);

            tipCo = itemView.findViewById(R.id.tipCo);
        }
    }
}
