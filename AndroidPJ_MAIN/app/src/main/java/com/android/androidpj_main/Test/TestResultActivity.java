package com.android.androidpj_main.Test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.androidpj_main.Main.MainActivity;
import com.android.androidpj_main.R;

import java.util.ArrayList;

// 지은 21.01.14 **************************************************
// 결과 도출 ( 퍼스널 컬러에 대한 )
public class TestResultActivity extends Activity {

    Button test_end;
    String test01, test02, test03, test04, test05;

    TextView tv_testR;

    ArrayList<String> color;

    ImageView iv_testR;

    int colorCool=0;
    int colorWarm=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        iv_testR = findViewById(R.id.iv_testR);

        test_end = findViewById(R.id.test_end);
        color = new ArrayList<String>();

        test_end.setOnClickListener(nextOnClickListener);
        //

        Intent intent = getIntent();
        test01 = intent.getStringExtra("test01");
        test02 = intent.getStringExtra("test02");
        test03 = intent.getStringExtra("test03");
        test04 = intent.getStringExtra("test04");
        test05 = intent.getStringExtra("test05");

        tv_testR = findViewById(R.id.tv_testR);
        //tv_testR.setText(test01 + " + " + test02 + " + " + test03 + " + " + test04 + " + " + test05);
        color.add(test01);
        color.add(test02);
        color.add(test03);
        color.add(test04);
        color.add(test05);
    }


    // 값을 비교하여 결과 값을 알려준다.
    @Override
    protected void onResume() {
        super.onResume();
        for (int i =0; i<color.size();i++){

            if(color.get(i).toString().equals("warm")){
                colorWarm++;
            }else if(color.get(i).toString().equals("cool")){
                colorCool++;
            }
        }

        if(colorCool>colorWarm){
            tv_testR.setText("쿨톤");
            //iv_testR.setImageResource(R.drawable.cool_result);
        }else if(colorWarm > colorCool){
            tv_testR.setText("웜톤");
            //iv_testR.setImageResource(R.drawable.warm_result);
        }
    }
    //

    // 메인으로 가는 버튼 클릭시 화면 전환
    View.OnClickListener nextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_end:
                    Intent intent = new Intent(TestResultActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };



}