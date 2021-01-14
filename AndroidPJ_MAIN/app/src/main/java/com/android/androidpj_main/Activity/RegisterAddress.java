package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.android.androidpj_main.R;

public class RegisterAddress extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_address);

        // 타이틀
        setTitle("배송지 등록");
    }
}