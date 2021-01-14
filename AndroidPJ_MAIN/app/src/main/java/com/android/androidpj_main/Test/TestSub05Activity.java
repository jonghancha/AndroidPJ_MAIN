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
// 테스트 5
public class TestSub05Activity extends Activity {

    Button test_pre5;

    Button warm05, cool05;

    String test01, test02, test03, test04, test05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sub05);
        setTitle("테스트 sub05입니다.");

        // sub4 화면으로 다시 이동을 위한 버튼 선언
        test_pre5 = findViewById(R.id.test_pre5);


        test_pre5.setOnClickListener(activityClickListener);
        //

        warm05 = findViewById(R.id.warm05);
        cool05 = findViewById(R.id.cool05);

        warm05.setOnClickListener(testClickListener);
        cool05.setOnClickListener(testClickListener);
        //

        //
        Intent intent = getIntent();
        test01 = intent.getStringExtra("test01");
        test02 = intent.getStringExtra("test02");
        test03 = intent.getStringExtra("test03");
        test04 = intent.getStringExtra("test04");


        //
    }

    View.OnClickListener testClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.warm05:
                    test05 = "warm";
                    Intent intentW = new Intent(TestSub05Activity.this, TestResultActivity.class);
                    intentW.putExtra("test01", test01);
                    intentW.putExtra("test02", test02);
                    intentW.putExtra("test03", test03);
                    intentW.putExtra("test04", test04);
                    intentW.putExtra("test05", test05);
                    startActivity(intentW);
                    finish();
                    break;

                case R.id.cool05:
                    test05 = "cool";
                    Intent intentC = new Intent(TestSub05Activity.this, TestResultActivity.class);
                    intentC.putExtra("test01", test01);
                    intentC.putExtra("test02", test02);
                    intentC.putExtra("test03", test03);
                    intentC.putExtra("test04", test04);
                    intentC.putExtra("test05", test05);
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
                case R.id.test_pre5:
                    Intent intent_pre = new Intent(TestSub05Activity.this, TestSub04Activity.class);
                    startActivity(intent_pre);
                    finish();
                    break;
            }
        }
    };


}