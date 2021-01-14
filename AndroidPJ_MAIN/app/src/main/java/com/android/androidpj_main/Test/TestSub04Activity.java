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
// 테스트 4
public class TestSub04Activity extends Activity {

    Button test_pre4;

    Button warm04, cool04;

    String test01, test02, test03, test04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sub04);
        setTitle("테스트 sub04입니다.");

        // sub3 화면으로 다시 이동을 위한 버튼 선언
        test_pre4 = findViewById(R.id.test_pre4);


        test_pre4.setOnClickListener(activityClickListener);
        //

        warm04 = findViewById(R.id.warm04);
        cool04 = findViewById(R.id.cool04);

        warm04.setOnClickListener(testClickListener);
        cool04.setOnClickListener(testClickListener);
        //

        Intent intent = getIntent();
        test01 = intent.getStringExtra("test01");
        test02 = intent.getStringExtra("test02");
        test03 = intent.getStringExtra("test03");

    }



    View.OnClickListener testClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.warm04:
                    test04 = "warm";
                    Intent intentW = new Intent(TestSub04Activity.this, TestSub05Activity.class);
                    intentW.putExtra("test01", test01);
                    intentW.putExtra("test02", test02);
                    intentW.putExtra("test03", test03);
                    intentW.putExtra("test04", test04);
                    startActivity(intentW);
                    finish();
                    break;

                case R.id.cool04:
                    test04 = "cool";
                    Intent intentC = new Intent(TestSub04Activity.this, TestSub05Activity.class);
                    intentC.putExtra("test01", test01);
                    intentC.putExtra("test02", test02);
                    intentC.putExtra("test03", test03);
                    intentC.putExtra("test04", test04);
                    startActivity(intentC);
                    finish();
                    break;
            }
        }
    };


    // 이전 혹은 다음을 위한 버튼 액션
    View.OnClickListener activityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.test_pre4:
                    Intent intent_pre = new Intent(TestSub04Activity.this, TestSub03Activity.class);
                    startActivity(intent_pre);
                    finish();
                    break;
            }
        }
    };


}