package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.androidpj_main.Adapter.CartAdapter;

import com.android.androidpj_main.Bean.Cart;

import com.android.androidpj_main.NetworkTask.CartNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements OnChangeCheckedPrice{

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
    Button btnCartDelete;
    Button btnCartToOrder;

    // 전체 가격 보내기
    String sendTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        setTitle("장바구니");

        userEmail = PreferenceManager.getString(CartActivity.this,"email");
        cartRecyclerView = findViewById(R.id.cart_recycleView);

        btnCartToOrder = findViewById(R.id.btn_cart_order);

        btnCartDelete = findViewById(R.id.btn_cart_delete);

        btnCartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cart.size() != 0) {
                    cartAdapter.connectDeleteData();
                    connectGetCart();
                    btnCartToOrder.setText("총 0원 주문하기");



                } else {
                    btnCartToOrder.setClickable(false);
                }


            }
        });



        // 결제창으로 넘어가기
        btnCartToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < cartAdapter.sendToOrder().size(); i++) {
                    Log.v(TAG, "보낼 값 ;;;; " + i + " 번 째" + cartAdapter.sendToOrder().get(i).getPrdName());
                    Log.v(TAG, "보낼 값 ;;;; " + i + " 번 째" + cartAdapter.sendToOrder().get(i).getPrdPrice());
                    Log.v(TAG, "보낼 값 ;;;; " + i + " 번 째" + cartAdapter.sendToOrder().get(i).getCartQty());

                }


                if (cartAdapter.sendToOrder().size() == 0){
                    Toast.makeText(CartActivity.this, "상품을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    String str = String.valueOf(btnCartToOrder.getText());
                    int end = str.indexOf("원");
                    String strSubbed =  str.substring(2,end);
                    Log.v(TAG, "총 가격 +++++++++++++++++++++++++++" + strSubbed);
                    Intent intent = new Intent(CartActivity.this, PurchaseActivity.class);
                    intent.putExtra("cartData", cartAdapter.sendToOrder());
                    intent.putExtra("totalPrice", strSubbed);
                    startActivity(intent);
                }

            }
        });



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

//        cartAdapter.setOnChangeCheckedPrice(new OnChangeCheckedPrice() {
//            @Override
//            public void changedPrice(int totalPrice) {
//                Log.v(TAG, "**메인 가격변경 리스너 들어옴 **");
//                btnCartToOrder.setText("총" + totalPrice + "원 주문하기");
//            }
//        });
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

            cartAdapter = new CartAdapter(CartActivity.this, R.layout.item_cart, cart, this);
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
        btnCartToOrder =findViewById(R.id.btn_cart_order);
        btnCartToOrder.setText("총 0원 주문하기");
    }


    @Override
    public void changedPrice(int totalPrice) {
        Log.v(TAG, "**메인 가격변경 리스너 들어옴 **");
        btnCartToOrder.setText("총 " + totalPrice + "원 주문하기");

    }
}