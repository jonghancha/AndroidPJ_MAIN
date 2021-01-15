package com.android.androidpj_main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Activity.PreferenceManager;
import com.android.androidpj_main.Activity.ProductViewActivity;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.NetworkTask.CUDNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ProductHolder> {

    final static String TAG = "LikeAdapter";

    ///////////////////////////////////////////////////////////////////////////
    //
    // 21.01.08 세미 생성
    //
    ///////////////////////////////////////////////////////////////////////////

    Context mContext = null;
    int layout = 0;
    ArrayList<Product> data = null;
    LayoutInflater inflater = null;
    String urlAddr;

    public LikeAdapter(Context mContext, int layout, ArrayList<Product> data){
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
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_like, parent, false);
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
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 90%;\"" +
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
//                intent.putExtra("prdName", data.get(position).getPrdName());
//                intent.putExtra("ctgType", data.get(position).getCtgType());
//                intent.putExtra("prdBrand", data.get(position).getPrdBrand());
                intent.putExtra("prdPrice", data.get(position).getPrdPrice());
//                intent.putExtra("prdFilename", data.get(position).getPrdFilename());
//                intent.putExtra("prdDFilename", data.get(position).getPrdDFilename());
//                intent.putExtra("prdNFilename", data.get(position).getPrdNFilename());
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
        protected ImageButton ib_likebtn;
        protected TextView tv_likebrand;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            wv_likeimg = itemView.findViewById(R.id.wv_likeimg);
            tv_likename = itemView.findViewById(R.id.tv_likename);
            tv_likeprice = itemView.findViewById(R.id.tv_likeprice);
            ib_likebtn = itemView.findViewById(R.id.ib_likebtn);
            tv_likebrand = itemView.findViewById(R.id.tv_likebrand);

            // WebView 세팅
            // Web Setting
            WebSettings webSettings = wv_likeimg.getSettings();
            webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
            webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
            wv_likeimg.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투

            ib_likebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //networktask 사용해서 like 지우기
                   int prdNo = data.get(getAdapterPosition()).getPrdNo();
                   // 로그인한 아이디 받아와서 아이디 넘겨주기. 그 아이디에 있는 것을 삭제해야하기 때문.
                    String email = PreferenceManager.getString(v.getContext(), "email");
                   urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likeDel.jsp?prdNo="+prdNo+"&email="+email;
                    connectDeleteData();

                    data.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());

                   Toast.makeText(v.getContext(),"찜목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // 찜 삭제
    public void connectDeleteData() {
        try {
            CUDNetworkTask deletenetworkTask = new CUDNetworkTask(mContext, urlAddr);
            deletenetworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void remove(int position){
//        try{
//            data.remove(position);
//            notifyItemRemoved(position);    // 새로고침
//        }catch (IndexOutOfBoundsException ex){
//            ex.printStackTrace();
//        }
//    }

}//-----------
