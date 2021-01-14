package com.android.androidpj_main.Test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.R;

// 지은 21.01.14 **************************************************
// 테스트 2
public class TestSub02Activity extends Activity {

    Button test_pre2;

    Button warm02, cool02;
    String test01, test02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sub02);
        setTitle("테스트 sub02입니다.");
        Intent intent = getIntent();
        test01 = intent.getStringExtra("test01");

        // sub1 화면으로 다시 이동을 위한 버튼 선언
        test_pre2 = findViewById(R.id.test_pre2);


        test_pre2.setOnClickListener(activityClickListener);
        //


        warm02 = findViewById(R.id.warm02);
        cool02 = findViewById(R.id.cool02);

        warm02.setOnClickListener(testClickListener);
        cool02.setOnClickListener(testClickListener);
        //


    }

    View.OnClickListener testClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.warm02:
                    test02 = "warm";
                    Intent intentW = new Intent(TestSub02Activity.this, TestSub03Activity.class);
                    intentW.putExtra("test01", test01);
                    intentW.putExtra("test02", test02);
                    startActivity(intentW);
                    finish();
                    break;

                case R.id.cool02:
                    test02 = "cool";
                    Intent intentC = new Intent(TestSub02Activity.this, TestSub03Activity.class);
                    intentC.putExtra("test01", test01);
                    intentC.putExtra("test02", test02);
                    startActivity(intentC);
                    finish();
                    break;
            }
        }
    };




    // 이전 위한 버튼 액션
    View.OnClickListener activityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.test_pre2:
                    Intent intent_pre = new Intent(TestSub02Activity.this, TestSub01Activity.class);
                    startActivity(intent_pre);
                    finish();
                    break;
            }
        }
    };


}