package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.androidpj_main.Bean.Cart;
import com.android.androidpj_main.R;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {

    final static String TAG = "PurchaseActivity";
    ArrayList<Cart> getCartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        

        Intent intent = getIntent();
        getCartData = (ArrayList<Cart>) intent.getSerializableExtra("cartData");

        for (int i = 0; i < getCartData.size(); i++) {
            Log.v(TAG, "받은 값 ;;;; " + i + " 번 째" + getCartData.get(i).getPrdName());
            Log.v(TAG, "받은 값 ;;;; " + i + " 번 째" + getCartData.get(i).getPrdPrice());
            Log.v(TAG, "받은 값 ;;;; " + i + " 번 째" + getCartData.get(i).getCartQty());
        }



        LinearLayout ll;
        ll = findViewById(R.id.ll);


        Button button1 = findViewById(R.id.btn_makeET);
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
}