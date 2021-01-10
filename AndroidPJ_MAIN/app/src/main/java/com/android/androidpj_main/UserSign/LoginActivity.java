package com.android.androidpj_main.UserSign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.androidpj_main.Main.MainActivity;
import com.android.androidpj_main.R;


public class LoginActivity extends Activity {

    final static String TAG = "LoginActivity";

    Button gomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gomain = findViewById(R.id.btn_main);

        gomain.setOnClickListener(gogo);


    }

    View.OnClickListener gogo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };



}//-------------------