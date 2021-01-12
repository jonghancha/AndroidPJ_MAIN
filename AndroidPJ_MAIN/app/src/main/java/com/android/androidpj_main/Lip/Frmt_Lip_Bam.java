package com.android.androidpj_main.Lip;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Adapter.ProductAdapter;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.NetworkTask.LikeNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

// 지은 추가 21.01.13 ***************************
public class Frmt_Lip_Bam extends Fragment {

    View v;
    String urlAddr = null;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    RecyclerView productColor_B_recycleView;
    GridLayoutManager gridLayoutManager;
    final static String TAG = "Frmt_Lip_Bam";
    String color;


    public Frmt_Lip_Bam(String color) {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_lip_bam,container,false);

        productColor_B_recycleView = v.findViewById(R.id.productColor_B_recycleView);

        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/C_bam_ProductList.jsp?prdColor=" + color;
        connectGetProduct();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    //메소드 = 상품을 띄우는
    private void connectGetProduct(){
        try {
            LikeNetworkTask networkTask = new LikeNetworkTask(getContext(), urlAddr); //onCreate 에 urlAddr 이 선언된것이 들어옴

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = networkTask.execute().get();
            gridLayoutManager = new GridLayoutManager(getContext(), 2);
            products = (ArrayList<Product>) object;

            productAdapter = new ProductAdapter(getContext(), R.layout.item_product, products);
            productColor_B_recycleView.setAdapter(productAdapter);
            productColor_B_recycleView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            productColor_B_recycleView.setLayoutManager(gridLayoutManager);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    //입력 되어도 리스트에 실시간으로 추가 되게 만들어줌(유지)
    @Override
    public void onResume() {
        super.onResume();
        connectGetProduct();
        Log.v(TAG, "onResume()");
    }


}
