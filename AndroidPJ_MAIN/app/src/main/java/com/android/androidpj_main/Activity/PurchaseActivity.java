package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.androidpj_main.Adapter.CartAdapter;
import com.android.androidpj_main.Bean.Cart;
import com.android.androidpj_main.NetworkTask.CartNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity implements OnChangeCheckedPrice{

    final static String TAG = "PurchaseActivity";
    ArrayList<Cart> getCartData;

    String urlAddr = null;
    ArrayList<Cart> cart;
    CartAdapter cartAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView purchaseRecyclerView;

    String macIP = ShareVar.macIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        // 카트에서 선택한 값 받아오기
        Intent intent = getIntent();
        getCartData = (ArrayList<Cart>) intent.getSerializableExtra("cartData");

        // 에딧텍스트 만들어주기 위해 아이디 받아옴
        LinearLayout ll;
        ll = findViewById(R.id.ll);

        Button button1 = findViewById(R.id.btn_makeET);
        purchaseRecyclerView = findViewById(R.id.purchase_recycleView);
//        Button button2 = findViewById(R.id.button2);

        // 숫자 개수 만큼 editText 추가 생성
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (ll.getChildAt(0) != null){
                    ll.removeAllViews();
                }else {
                    et.setLayoutParams(p);
                    et.setText("editText 번");

                    ll.addView(et);
                    Log.v("Pur", String.valueOf(et.getText()));

                }
            }
        });

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ll.removeAllViews();
//            }
//        });

    }

    // 상품을 띄우는
    private void connectGetCart() {



            cartAdapter = new CartAdapter(PurchaseActivity.this, R.layout.item_purchase, getCartData, this);
        purchaseRecyclerView.setAdapter(cartAdapter);
        purchaseRecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(PurchaseActivity.this);
        purchaseRecyclerView.setLayoutManager(layoutManager);


    }

    //입력 되어도 리스트에 실시간으로 추가 되게 만들어줌(유지)
    @Override
    public void onResume() {
        super.onResume();
        connectGetCart();
        Log.v(TAG, "onResume()");
    }

    @Override
    public void changedPrice(int totalPrice) {

    }
}// ----