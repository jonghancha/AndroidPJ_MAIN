package com.android.androidpj_main.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.androidpj_main.R;

public class TestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        setTitle("테스트 메인입니다.");
    }
}