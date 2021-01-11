package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.androidpj_main.Main.MainActivity;
import com.android.androidpj_main.R;

public class EmptyCartActivity extends Activity {

    Button btnCartHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_cart);

        btnCartHome = findViewById(R.id.btn_cart_home);

        btnCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmptyCartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}