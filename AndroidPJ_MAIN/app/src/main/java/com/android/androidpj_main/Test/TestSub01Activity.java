package com.android.androidpj_main.Test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.R;

// 지은 21.01.14 **************************************************
// 테스트 1
public class TestSub01Activity extends Activity {

    Button warm01, cool01;
    String test01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sub01);
        setTitle("테스트 sub01입니다.");

        warm01 = findViewById(R.id.warm01);
        cool01 = findViewById(R.id.cool01);

        warm01.setOnClickListener(testClickListener);
        cool01.setOnClickListener(testClickListener);

    }

    View.OnClickListener testClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.warm01:
                    test01 = "warm";
                    Toast.makeText(TestSub01Activity.this, test01 + "sub01", Toast.LENGTH_SHORT).show();
                    Intent intentW = new Intent(TestSub01Activity.this, TestSub02Activity.class);
                    intentW.putExtra("test01", test01);
                    startActivity(intentW);
                    finish();
                    break;

                case R.id.cool01:
                    test01 = "cool";
                    Toast.makeText(TestSub01Activity.this, test01 + "sub01", Toast.LENGTH_SHORT).show();
                    Intent intentC = new Intent(TestSub01Activity.this, TestSub02Activity.class);
                    intentC.putExtra("test01", test01);
                    startActivity(intentC);
                    finish();
                    break;
            }
        }
    };




}