package com.android.androidpj_main.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.Bean.User;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class MyViewActivity extends AppCompatActivity {

    // 21.01.10 지은 추가 ********************************
    // 제일 상단에 나의 정보 띄움
    TextView MyName, MyEmail;
    // 하단의 목록 넘어갈수 있는 선언
    LinearLayout MyUpdate, MyPwUpdate, MyLogout, MyDelete;

    ArrayList<User> users;
    // 로그인한 아이디에 대한 정보 띄움
    String urlAddr_my = null;

    // 로그인한 아이디에 대한 비밀번호 수정 창
    String urlAddr_pw = null;

    // 회원탈퇴 입력시 deletedate 에 현재 날짜가 update가 된다.
    String urlAddr_de = null;

    // 비밀번호 수정 다이어로그 창에 띄울 현재 비밀번호
    String pass;
    //****************************************************************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myview);

        // 지은 21/01/10 정보 띄우기****************************
        // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
//        String checkId = PreferenceManager.getString(MyViewActivity.this,"id");

        // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
//        urlAddr_my = "http://" + ShareVar.macIP + ":8080/test/mySelect.jsp?user_userId=" + checkId;
//        getUserDate();  // 띄우기 위한 메소드

        MyName = findViewById(R.id.MyName);
        MyEmail = findViewById(R.id.MyEmail);

//        MyName.setText(users.get(0).getUserName());
//        MyEmail.setText(users.get(0).getUserEmail());

//        pass = users.get(0).getUserPw();
        //*******************************************

        // 지은 21/01/10 정보 수정 & 삭제 관련****************************
        MyUpdate = findViewById(R.id.MyUpdate);
        MyPwUpdate = findViewById(R.id.MyPwUpdate);
        MyLogout = findViewById(R.id.MyLogout);
        MyDelete = findViewById(R.id.MyDelete);

        MyUpdate.setOnClickListener(MyVListener);
        MyPwUpdate.setOnClickListener(MyVListener);
        MyLogout.setOnClickListener(MyVListener);
        MyDelete.setOnClickListener(MyVListener);
        //****************************************************************
    }


    // 정보 수정 & 삭제 관련 버튼 클릭
    View.OnClickListener MyVListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.MyUpdate:
                    Toast.makeText(MyViewActivity.this, "나의 정보 수정 클릭", Toast.LENGTH_SHORT).show();
                    break;

                    //비밀번호 수정
                case R.id.MyPwUpdate:
                    Toast.makeText(MyViewActivity.this, "나의 비밀번호 수정 클릭", Toast.LENGTH_SHORT).show();
//                    final LinearLayout linear = (LinearLayout) View.inflate(MyViewActivity.this, R.layout.pass, null);
//                    pass_pass = linear.findViewById(R.id.pass_pass);
//                    pass_pass.setText(pass);
//
//                    new AlertDialog.Builder(MyViewActivity.this)
//                            .setTitle("나의 비밀번호 수정")
//                            .setIcon(R.drawable.group)
//
//                            // 위에서 선언한
//                            .setView(linear)
//                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String checkId = PreferenceManager.getString(MyViewActivity.this,"id");
//                                    // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
//                                    urlAddr_pw = "http://" + ShareVar.macIP + ":8080/test/mySelect.jsp?user_userId=" + checkId;
//                                    getUserDate();  // 띄우기 위한 메소드
//                                    // linear에 있는 아이 이므로 앞에 넣어줘야한다.
//
//
//
//                                    EditText paUpda = linear.findViewById(R.id.pass_Upda); // 현재 비밀번호를 띄울곳
//
//
//                                    String passupdate = paUpda.getText().toString();
//
//                                    urlAddr = "http://" + ShareVar.macIP + ":8080/test/passUpdate.jsp?user_userId=" + checkId;  // ?(물음표) 주의
//
//                                    urlAddr = urlAddr +"&userPw="+ passupdate;
//                                    connectUpdatePass();
//                                    Toast.makeText(MyViewActivity.this, checkId+" 님의 비밀번호가 수정되었습니다.", Toast.LENGTH_SHORT).show();
//
//                                }
//                            })
//                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(MyViewActivity.this, pass, Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .show();



                    break;
                case R.id.MyLogout:
                    Toast.makeText(MyViewActivity.this, "로그아웃 클릭", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.MyDelete:
                    Toast.makeText(MyViewActivity.this, "회원 탈퇴 클릭", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    // 내가 로그인한 id값에 대한 이름과 연락처를 불러옴
//    private void getUserDate(){
//        try {
//            UserNetworkTask networkTask = new UserNetworkTask(MyViewActivity.this, urlAddr1);
//            Object obj = networkTask.execute().get();
//            users = (ArrayList<User>) obj;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    // 비밀번호 수정
//    private void connectUpdatePass(){
//        try {
//            CUDNetworkTask insnetworkTask = new CUDNetworkTask(MyViewActivity.this, urlAddr);
//            insnetworkTask.execute().get();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}