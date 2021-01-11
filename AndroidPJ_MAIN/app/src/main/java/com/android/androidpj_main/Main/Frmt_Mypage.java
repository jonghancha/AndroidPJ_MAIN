package com.android.androidpj_main.Main;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.androidpj_main.Activity.MyViewActivity;
import com.android.androidpj_main.Bean.User;
import com.android.androidpj_main.NetworkTask.UserNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class Frmt_Mypage extends Fragment {

    View v;
    final static String TAG = "Frmt_Mypage";

    // 21.01.10 지은 추가 ********************************
    LinearLayout LH_call, LH_email, LH_often;
    LinearLayout myOrder, myReview;
    ImageView mySetting;

    TextView MyMainName, MyColor;
    ArrayList<User> users_mypage;
    String urlAddr_My = null; // 로그인한 아이디에 대한 정보 띄움 ( 이름과 퍼스널 컬러 )
    //****************************************************************


    public Frmt_Mypage() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_mypage,container,false);

        // 지은 21/01/10 정보 띄우기****************************
        // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
        String checkEmail = PreferenceManager.getString(getActivity(),"email");



        // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
        urlAddr_My = "http://" + ShareVar.macIP + ":8080/JSP/mySelect.jsp?userEmail=" + checkEmail;
        getUserDate();  // 띄우기 위한 메소드
        MyMainName = v.findViewById(R.id.MyMainName);
        MyColor = v.findViewById(R.id.MyColor);

        MyMainName.setText(users_mypage.get(0).getUserName());
        MyColor.setText(users_mypage.get(0).getUserColor());
        //*******************************************


        // 지은 21/01/10 정보 & 조회 관련****************************
        mySetting = v.findViewById(R.id.mySetting);

        myOrder = v.findViewById(R.id.myOrder);
        myReview = v.findViewById(R.id.myReview);

        mySetting.setOnClickListener(settingListener);
        myOrder.setOnClickListener(settingListener);
        myReview.setOnClickListener(settingListener);
        //*******************************************

        // 지은 21/01/10 문의 관련****************************
        LH_call = v.findViewById(R.id.LH_call);
        LH_email = v.findViewById(R.id.LH_email);
        LH_often = v.findViewById(R.id.LH_often);

        LH_call.setOnClickListener(questionListener);
        LH_email.setOnClickListener(questionListener);
        LH_often.setOnClickListener(questionListener);
        //*******************************************




        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    // 21.01.10 지은 추가 ********************************
    // 내가 로그인한 id값에 대한 이름과 연락처를 불러옴
    private void getUserDate(){
        try {
            UserNetworkTask networkTask = new UserNetworkTask(getActivity(), urlAddr_My);
            Object obj = networkTask.execute().get();
            users_mypage = (ArrayList<User>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 정보 & 조회 관련 버튼 클릭
    View.OnClickListener settingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mySetting:
                    Toast.makeText(getActivity(), "내정보 상세 수정", Toast.LENGTH_SHORT).show();
                    Intent Mintent = new Intent(getActivity(), MyViewActivity.class);
                    startActivity(Mintent);
                    break;
                case R.id.myOrder:
                    Toast.makeText(getActivity(), "나의 주문/배송 조회", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myReview:
                    Toast.makeText(getActivity(), "나의 리뷰 조회", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    // 문의 관련 버튼 클릭
    View.OnClickListener questionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.LH_often:
                    Toast.makeText(getActivity(), "자주묻는 질문 클릭", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.LH_call:
                    Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:012-3456-7890"));
                    startActivity(mIntent);
                    break;

                case R.id.LH_email:
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("plain/text");
                    String[] address = {"qkrwldms011@naver.com"};
                    email.putExtra(Intent.EXTRA_EMAIL, address);
                    email.putExtra(Intent.EXTRA_SUBJECT, "LIPHAE 에게 문의하실 제목을 적어주세요.");
                    email.putExtra(Intent.EXTRA_TEXT, "LIPHAE 에게 문의 하실 내용을 적어주세요.");
                    startActivity(email);
                    break;
            }
        }
    };


    // ****************************************************************


}//---- 끝 ------
