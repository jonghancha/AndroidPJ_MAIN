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
// 테스트 3
public class TestSub03Activity extends Activity {

    Button test_pre3;

    Button warm03, cool03;
    String test01, test02, test03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sub03);
        setTitle("테스트 sub03입니다.");

        // sub2 화면으로 다시 이동을 위한 버튼 선언
        test_pre3 = findViewById(R.id.test_pre3);


        test_pre3.setOnClickListener(activityClickListener);
        //

        warm03 = findViewById(R.id.warm03);
        cool03 = findViewById(R.id.cool03);

        warm03.setOnClickListener(testClickListener);
        cool03.setOnClickListener(testClickListener);
        //

        Intent intent = getIntent();
        test01 = intent.getStringExtra("test01");
        test02 = intent.getStringExtra("test02");

    }

    View.OnClickListener testClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.warm03:
                    test03 = "warm";
                    Intent intentW = new Intent(TestSub03Activity.this, TestSub04Activity.class);
                    intentW.putExtra("test01", test01);
                    intentW.putExtra("test02", test02);
                    intentW.putExtra("test03", test03);
                    startActivity(intentW);
                    finish();
                    break;

                case R.id.cool03:
                    test03 = "cool";
                    Intent intentC = new Intent(TestSub03Activity.this, TestSub04Activity.class);
                    intentC.putExtra("test01", test01);
                    intentC.putExtra("test02", test02);
                    intentC.putExtra("test03", test03);
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
                case R.id.test_pre3:
                    Intent intent_pre = new Intent(TestSub03Activity.this, TestSub02Activity.class);
                    startActivity(intent_pre);
                    finish();
                    break;
            }
        }
    };


}