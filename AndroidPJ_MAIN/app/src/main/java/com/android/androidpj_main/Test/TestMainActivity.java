package com.android.androidpj_main.Test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.androidpj_main.Activity.MyViewActivity;
import com.android.androidpj_main.Bean.User;
import com.android.androidpj_main.Main.PreferenceManager;
import com.android.androidpj_main.NetworkTask.UserNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class TestMainActivity extends Activity {

    TextView test_Name;
    Button test_start;

    ImageView iv_tip;
    ArrayList<User> users_mypage;
    String urlAddr_My = null; // 로그인한 아이디에 대한 정보 띄움 ( 이름과 퍼스널 컬러 )

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        // 지은 21/01/10 정보 띄우기****************************
        // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
        String email = PreferenceManager.getString(TestMainActivity.this, "email");
//        Toast.makeText(getContext(), "email값:::::::;"+ email, Toast.LENGTH_SHORT).show();



        // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
        urlAddr_My = "http://" + ShareVar.macIP + ":8080/JSP/mySelect.jsp?userEmail=" + email;
        getUserDate();  // 띄우기 위한 메소드
        // 로그인한 아이디의 이름을 불러와야한다.
        test_Name = findViewById(R.id.test_Name);

        test_Name.setText("❤️ " + users_mypage.get(0).getUserName() + " ❤️");
        //*******************************************



        // tip 버튼 눌렀을 때 설명을 띄우는 다이어로그 출력
        iv_tip = findViewById(R.id.iv_tip);
        iv_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout linearT = (LinearLayout) View.inflate(TestMainActivity.this, R.layout.dia_tip, null);
                new AlertDialog.Builder(TestMainActivity.this)
                        // 위에서 선언한
                        .setView(linearT)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });


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

    // 21.01.10 지은 추가 ********************************
    // 내가 로그인한 id값에 대한 이름과 연락처를 불러옴
    private void getUserDate(){
        try {
            UserNetworkTask networkTask = new UserNetworkTask(TestMainActivity.this, urlAddr_My);
            Object obj = networkTask.execute().get();
            users_mypage = (ArrayList<User>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}