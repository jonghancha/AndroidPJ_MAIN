package com.android.androidpj_main.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.androidpj_main.R;

public class TestMainActivity extends AppCompatActivity {

    TextView test_Name;
    Button test_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        setTitle("테스트 메인입니다.");

        // 로그인한 아이디의 이름을 불러와야한다.
        test_Name = findViewById(R.id.test_Name);

        // 테스트 시작 버튼 선언
        test_start = findViewById(R.id.test_start);
        test_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestMainActivity.this, TestSub01Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}