package com.android.androidpj_main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Adapter.TipAdapter;
import com.android.androidpj_main.Bean.Tip;
import com.android.androidpj_main.NetworkTask.TipNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

// 제품 검색 (한줄-가로)
public class HoneyTipActivity extends AppCompatActivity {

    // 지은 수정중 21.01.13 ***************************
    final static String TAG = "HoneyTipActivity";
    String urlAddr = null;
    ArrayList<Tip> tips;
    TipAdapter tipAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView tip_recyclerView;


    //
    TipSheet bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honey_tip);
        setTitle("LIPHAE [쿨팁 차차]");

        findViewById(R.id.tip_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet = new TipSheet();
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });

        tips = new ArrayList<Tip>();

        tip_recyclerView = findViewById(R.id.tip_recycleView);

        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/HoneyTipList.jsp?";

    }

    public void changeText(String text) {
         String url = urlAddr+"tipTitle="+text;
         connectGetData(url);
    }


    //메소드 = 로그인한 아이디값에 저장된 연락처 띄워주는
    private void connectGetData(String url){
        try {
            TipNetworkTask networkTask = new TipNetworkTask(HoneyTipActivity.this, url); //onCreate 에 urlAddr 이 선언된것이 들어옴

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = networkTask.execute().get();

            // tips = Arr... 하면 새로 고침 형식으로 하나만 보여주게 됨
            // tips.addAll .. 을 쓰는 이유는 쌓여야 하므로
            tips.addAll ((ArrayList<Tip>) object);

            tipAdapter = new TipAdapter(HoneyTipActivity.this, R.layout.item_tip, tips);
            tip_recyclerView.setAdapter(tipAdapter);
            tip_recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(HoneyTipActivity.this);
            tip_recyclerView.setLayoutManager(layoutManager);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    //입력 되어도 리스트에 실시간으로 추가 되게 만들어줌(유지)
    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");

    }

}