package com.android.androidpj_main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Adapter.ProductAdapter;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.NetworkTask.LikeNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

// 제품 리스트 (세로-두줄)
public class ProductSubList extends AppCompatActivity {

    // 지은 추가 21.01.11 ***************************

    final static String TAG = "ProductSubList";
    String urlAddr = null;
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    //    private RecyclerView.LayoutManager layoutManager;
    RecyclerView productSub_recycleView;
    GridLayoutManager gridLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productsub_list);
        Intent intent = getIntent();
        String lip = intent.getStringExtra("lip");
        setTitle("LIPHAE [" + lip + "]");

        productSub_recycleView = findViewById(R.id.productSub_recycleView);



        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/SProductList.jsp?ctgType=" + lip;
        connectGetProduct();
    }



    //메소드 = 상품을 띄우는
    private void connectGetProduct(){
        try {
            LikeNetworkTask networkTask = new LikeNetworkTask(ProductSubList.this, urlAddr); //onCreate 에 urlAddr 이 선언된것이 들어옴

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = networkTask.execute().get();
            gridLayoutManager = new GridLayoutManager(this, 2);
            products = (ArrayList<Product>) object;

            productAdapter = new ProductAdapter(ProductSubList.this, R.layout.item_product, products);
            productSub_recycleView.setAdapter(productAdapter);
            productSub_recycleView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
//            layoutManager = new LinearLayoutManager(SearchActivity.this);
            productSub_recycleView.setLayoutManager(gridLayoutManager);

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