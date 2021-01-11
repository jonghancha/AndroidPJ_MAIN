package com.android.androidpj_main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
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

        // 상품 이미지(이미지로 수정해야함)
        holder.iv_likeimg.setImageResource(R.mipmap.ic_launcher);
        // 상품 이름
        holder.tv_likename.setText(data.get(position).getPrdName());
        // 상품 가격
        holder.tv_likeprice.setText(String.valueOf(data.get(position).getPrdPrice()));

        int prdNo = data.get(position).getPrdNo();
        //Toast.makeText(mContext, "prdNo : " + prdNo, Toast.LENGTH_SHORT).show();


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
                intent.putExtra("prdName", data.get(position).getPrdName());

                v.getContext().startActivity(intent);

//                Bundle bundle = new Bundle();// 무언가를 담을 준비를 할 수 있는 보따리 or 꾸러미
//                bundle.putString("fromFrag1", "홍드로이드 프래그먼트 1");
                //FragmentTransaction transaction = v.getC

                Toast.makeText(v.getContext(), "상세보기 페이지 이동", Toast.LENGTH_SHORT).show();
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
        protected ImageView iv_likeimg;
        protected TextView tv_likename;
        protected TextView tv_likeprice;
        protected ImageButton ib_likebtn;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            iv_likeimg = itemView.findViewById(R.id.iv_likeimg);
            tv_likename = itemView.findViewById(R.id.tv_likename);
            tv_likeprice = itemView.findViewById(R.id.tv_likeprice);
            ib_likebtn = itemView.findViewById(R.id.ib_likebtn);

            ib_likebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //networktask 사용해서 like 지우기
                   int prdNo = data.get(getAdapterPosition()).getPrdNo();
                   // 로그인한 아이디 받아와서 아이디 넘겨주기. 그 아이디에 있는 것을 삭제해야하기 때문.
                   urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likeDel.jsp?prdNo="+prdNo;
                    connectDeleteData();
                   Toast.makeText(v.getContext(),"삭제되었습니다." + prdNo, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void connectDeleteData(){
        try{
            CUDNetworkTask deletenetworkTask = new CUDNetworkTask(mContext, urlAddr);
            deletenetworkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
     }






}//-----------
