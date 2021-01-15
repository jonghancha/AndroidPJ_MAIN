package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.androidpj_main.Main.MainActivity;
import com.android.androidpj_main.R;

public class OrderFinished extends AppCompatActivity {

    TextView ofUserName, ofUserTel, ofUserEmail, ofRequest, ofRcvName, ofRcvTel, ofRcvEmail;
    Button ofToHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finished);

        setTitle("구매 완료");

        ofUserName = findViewById(R.id.of_userName);
        ofUserTel = findViewById(R.id.of_userTel);
        ofUserEmail = findViewById(R.id.of_userEmail);
        ofRequest = findViewById(R.id.of_request);
        ofRcvName = findViewById(R.id.of_rCvName);
        ofRcvTel = findViewById(R.id.of_rCvTel);
        ofRcvEmail = findViewById(R.id.of_rCvEmail);

        Intent intent = getIntent();
        ofUserName.setText(intent.getStringExtra("buy_name"));
        ofUserTel.setText(intent.getStringExtra("buy_tel"));
        ofUserEmail.setText(intent.getStringExtra("buy_email"));
        ofRequest.setText(intent.getStringExtra("buy_request"));
        ofRcvName.setText(intent.getStringExtra("rcv_name"));
        ofRcvTel.setText(intent.getStringExtra("rcv_tel"));
        ofRcvEmail.setText(intent.getStringExtra("rcv_address"));

        ofToHome = findViewById(R.id.of_to_Home);
        ofToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OrderFinished.this, MainActivity.class);
                startActivity(intent1);
                finish();

            }
        });

    }
}