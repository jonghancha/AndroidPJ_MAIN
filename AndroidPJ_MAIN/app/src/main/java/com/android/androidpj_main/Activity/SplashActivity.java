package com.android.androidpj_main.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.androidpj_main.R;
import com.android.androidpj_main.UserSign.LoginActivity;

// 지은 완료 21.01.06 ***************************
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}