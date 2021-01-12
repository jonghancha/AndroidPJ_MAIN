package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.androidpj_main.Adapter.CartAdapter;

import com.android.androidpj_main.Bean.Cart;

import com.android.androidpj_main.NetworkTask.CartNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    final static String TAG = "CartActivity";
    String urlAddr = null;
    ArrayList<Cart> cart;
    CartAdapter cartAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView cartRecyclerView;
    //GridLayoutManager gridLayoutManager;

    String macIP = ShareVar.macIP;

    // 로그인한 id 받아오기
    String userEmail;

    CheckBox cart_cb_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setTitle("장바구니");

        userEmail = PreferenceManager.getString(CartActivity.this,"email");
        cartRecyclerView = findViewById(R.id.cart_recycleView);

        cart_cb_all = findViewById(R.id.cart_cb_all);

        cart_cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cart_cb_all.isChecked()){
                    cartAdapter.checkBoxOperation(true);
                }else {
                    cartAdapter.checkBoxOperation(false);
                }
            }
        });

        connectGetCart();
    }

    // 상품을 띄우는
    private void connectGetCart() {
        try {
            urlAddr = "http://" + macIP + ":8080/JSP/cart_select.jsp?userEmail=" + userEmail;
            CartNetworkTask cartNetworkTask = new CartNetworkTask(CartActivity.this, urlAddr, "getdata");

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = cartNetworkTask.execute().get();
            //gridLayoutManager = new GridLayoutManager(this, 2);
            cart = (ArrayList<Cart>) object;

            cartAdapter = new CartAdapter(CartActivity.this, R.layout.item_cart, cart);
            cartRecyclerView.setAdapter(cartAdapter);
            cartRecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(CartActivity.this);
            cartRecyclerView.setLayoutManager(layoutManager);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //입력 되어도 리스트에 실시간으로 추가 되게 만들어줌(유지)
    @Override
    public void onResume() {
        super.onResume();
        connectGetCart();
        Log.v(TAG, "onResume()");
    }
}