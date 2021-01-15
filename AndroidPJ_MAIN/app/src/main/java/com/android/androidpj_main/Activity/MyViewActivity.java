package com.android.androidpj_main.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.Bean.User;
import com.android.androidpj_main.NetworkTask.CUDNetworkTask;
import com.android.androidpj_main.NetworkTask.UserNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;
import com.android.androidpj_main.UserSign.LoginActivity;

import java.util.ArrayList;

// 21.01.10 지은 추가 ********************************
public class MyViewActivity extends AppCompatActivity {

    // 21.01.10 지은 추가 ********************************
    // 제일 상단에 나의 정보 띄움
    TextView MyName, MyEmail;
    // 하단의 목록 넘어갈수 있는 선언
    LinearLayout MyUpdate, MyPwUpdate, MyLogout, MyDelete;

    ArrayList<User> users;
    // 로그인한 아이디에 대한 정보 띄움
    String urlAddr_my = null;

    String urlAddr_up = null;
    EditText myDname, myDtel;
    TextView myDcolor;
    String name, tel, color;
    RadioButton myMwarm, myMcool;
    RadioGroup rgColor;

    // 로그인한 아이디에 대한 비밀번호 수정 창
    String urlAddr_pw = null;
    // 비밀번호 수정 다이어로그 창에 띄울 현재 비밀번호 불러오는 역할
    String pass;
    // 비밀번호 수정 다이어로그 창에 띄울 현재 비밀번호 띄우는 역할
    TextView pass_pass;

    // 회원탈퇴 입력시 deletedate 에 현재 날짜가 update가 된다.
    String urlAddr_de = null;


    //****************************************************************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myview);

        // 지은 21/01/10 정보 띄우기****************************
        // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
        String email = PreferenceManager.getString(MyViewActivity.this,"email");


        //지은 실험
        //String checkEmail = "qkrwldms011@naver.com";

        // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
        urlAddr_my = "http://" + ShareVar.macIP + ":8080/JSP/mySelect.jsp?userEmail=" + email;
        getUserDate();  // 띄우기 위한 메소드

        MyName = findViewById(R.id.MyName);
        MyEmail = findViewById(R.id.MyEmail);
//        MyName.setText(users.get(0).getUserName());
//        MyEmail.setText(users.get(0).getUserEmail());


        MyName.setText(users.get(0).getUserName());
        MyEmail.setText(users.get(0).getUserEmail());

        pass = users.get(0).getUserPw();


        name = users.get(0).getUserName();
        tel = users.get(0).getUserTel();
        color = users.get(0).getUserColor();
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
                // 나의 정보 수정
                case R.id.MyUpdate:
                    final LinearLayout linearM = (LinearLayout) View.inflate(MyViewActivity.this, R.layout.myupdate, null);
                    myDname = linearM.findViewById(R.id.myDname);
                    myDname.setText(name);
                    myDtel = linearM.findViewById(R.id.myDtel);
                    myDtel.setText(tel);
                    myDcolor = linearM.findViewById(R.id.myDcolor);

                    rgColor = linearM.findViewById(R.id.rgColor);

                    myDcolor.setText(color);
                    rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.myMwarm){
                                myDcolor.setText("웜톤");
                            }else if (checkedId == R.id.myMcool){
                                myDcolor.setText("쿨톤");
                            }
                        }
                    });



                    new AlertDialog.Builder(MyViewActivity.this)
                            .setTitle("나의 정보 수정")
//                            .setIcon(R.drawable.group)

                            // 위에서 선언한
                            .setView(linearM)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
                                    // String checkEmail = PreferenceManager.getString(getActivity(),"email");

                                    //지은 실험
                                    String checkEmail = "qkrwldms011@naver.com";
                                    // linear에 있는 아이 이므로 앞에 넣어줘야한다.
                                    String nameupdate = myDname.getText().toString();
                                    String telupdate = myDtel.getText().toString();
                                    String colorupdate = myDcolor.getText().toString();

                                    urlAddr_up = "http://" + ShareVar.macIP + ":8080/JSP/myUpdate.jsp?userEmail=" + checkEmail;

                                    urlAddr_up = urlAddr_up +"&userName="+ nameupdate + "&userTel=" + telupdate + "&userColor=" + colorupdate;
                                    connectUpdateInfo();
                                    Toast.makeText(MyViewActivity.this, checkEmail+" 님의 회원정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MyViewActivity.this, "회원정보 수정을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                    break;

                    //비밀번호 수정
                case R.id.MyPwUpdate:
                    Toast.makeText(MyViewActivity.this, "나의 비밀번호 수정 클릭", Toast.LENGTH_SHORT).show();
                    final LinearLayout linearP = (LinearLayout) View.inflate(MyViewActivity.this, R.layout.pass, null);
                    // 현재 비밀번호를 띄울곳
                    pass_pass = linearP.findViewById(R.id.pass_pass);
                    pass_pass.setText(pass);

                    new AlertDialog.Builder(MyViewActivity.this)
                            .setTitle("나의 비밀번호 수정")
//                            .setIcon(R.drawable.group)

                            // 위에서 선언한
                            .setView(linearP)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
                                    // String checkEmail = PreferenceManager.getString(getActivity(),"email");

                                    //지은 실험
                                    String checkEmail = "qkrwldms011@naver.com";
                                    // linear에 있는 아이 이므로 앞에 넣어줘야한다.
                                    EditText paUpda = linearP.findViewById(R.id.pass_Upda);
                                    String passupdate = paUpda.getText().toString();

                                    urlAddr_pw = "http://" + ShareVar.macIP + ":8080/JSP/passUpdate.jsp?userEmail=" + checkEmail;  // ?(물음표) 주의

                                    urlAddr_pw = urlAddr_pw +"&userPw="+ passupdate;
                                    connectUpdatePass();
                                    Toast.makeText(MyViewActivity.this, checkEmail+" 님의 비밀번호가 수정되었습니다.", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MyViewActivity.this, "비밀번호 수정을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                    break;

                case R.id.MyLogout:
                    Intent intent = new Intent(MyViewActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(MyViewActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.MyDelete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyViewActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage("탈퇴를 진행하시겠습니까?");

                    // Set Alert Title
                    builder.setTitle("경고");
                    builder.setCancelable(false);
                    builder.setPositiveButton("네",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int which)
                        {
                            // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
                            String email = com.android.androidpj_main.Main.PreferenceManager.getString(MyViewActivity.this, "email");


                            // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
                            urlAddr_de = "http://" + ShareVar.macIP + ":8080/JSP/myDelete.jsp?userEmail=" + email;
                            connectUpdateDelete();
                            Toast.makeText(MyViewActivity.this, "회원 탈퇴가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("아니오",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int which){
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();


                    break;
            }

        }
    };


    //내가 로그인한 id값에 대한 이름과 연락처를 불러옴
    private void getUserDate(){
        try {
            UserNetworkTask networkTask = new UserNetworkTask(MyViewActivity.this, urlAddr_my);
            Object obj = networkTask.execute().get();
            users = (ArrayList<User>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 회원정보 수정
    private void connectUpdateInfo(){
        try {
            CUDNetworkTask infonetworkTask = new CUDNetworkTask(MyViewActivity.this, urlAddr_up);
            infonetworkTask.execute().get();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 비밀번호 수정
    private void connectUpdatePass(){
        try {
            CUDNetworkTask passnetworkTask = new CUDNetworkTask(MyViewActivity.this, urlAddr_pw);
            passnetworkTask.execute().get();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 회원탈퇴
    private void connectUpdateDelete(){
        try {
            CUDNetworkTask deletenetworkTask = new CUDNetworkTask(MyViewActivity.this, urlAddr_de);
            deletenetworkTask.execute().get();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}