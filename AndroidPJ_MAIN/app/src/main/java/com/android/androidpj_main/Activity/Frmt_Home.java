package com.android.androidpj_main.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.androidpj_main.R;
import com.android.androidpj_main.Test.TestMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Frmt_Home extends Fragment {

    final static String TAG = "Frmt_Home";
    View v;


    // 21.01.07 지은 추가 ---------------
    //boolean 플래그는 main FAB가 열린 상태인지 닫힌 상태인지 알 수 있음.
    private boolean fabExpanded = false;
    Button gotest;
    FloatingActionButton fabMain;   // 메안 플로팅 버튼
    FloatingActionButton fabMake, fab_honey;

    //Linear layout holding the Make submenu
    LinearLayout layoutFabMake;
    //Linear layout holding the Honey submenu
    LinearLayout layoutFabHoney;
    //------------------------


    public Frmt_Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_home,container,false);


        // 지은 21.01.08-------------------------------
        gotest = v.findViewById(R.id.btn_test);
        fabMain = v.findViewById(R.id.fabMain);
        fabMake = v.findViewById(R.id.fabMake);
        fab_honey = v.findViewById(R.id.fab_honey);
        layoutFabMake = v.findViewById(R.id.layoutFabMake);
        layoutFabHoney = v.findViewById(R.id.layoutFabHoney);

        gotest.setOnClickListener(homeBtnClickListener);
        fabMain.setOnClickListener(homeBtnClickListener);
        fabMake.setOnClickListener(homeBtnClickListener);
        fab_honey.setOnClickListener(homeBtnClickListener);

        //플로팅 버튼 시작할때 숨기기
        closeSubMenusFab();
        //------------------------------------------------


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 지은 추가 21.01.09-------------------------------
    // 지은 home 쪽에서 쓰이는 버튼 기능 수정
    View.OnClickListener homeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_test:
                    Intent intent = new Intent(getActivity(), TestMainActivity.class);
                    startActivity(intent);
                    break;

                    // 플로팅 버튼 관련 -------------------------
                case R.id.fabMain:
                    if (fabExpanded == true) {
                        closeSubMenusFab();
                    } else {
                        openSubMenusFab();
                    }
                    break;

                case R.id.fab_honey:
                    Toast.makeText(getContext(), "꿀팁 차차", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.fabMake:
                    Toast.makeText(getContext(), "메이크업 영상", Toast.LENGTH_SHORT).show();
                    break;
                // 플로팅 버튼 관련 -------------------------
            }
        }
    };//------------------------------------------------






    // 지은 21.01.08-------------------------------
    // 플로팅 서브메뉴 숨기기
    private void closeSubMenusFab() {
        layoutFabMake.setVisibility(View.INVISIBLE);
        layoutFabHoney.setVisibility(View.INVISIBLE);
        fabMain.setImageResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    // 플로팅 서브메뉴 나타내기
    private void openSubMenusFab() {
        layoutFabMake.setVisibility(View.VISIBLE);
        layoutFabHoney.setVisibility(View.VISIBLE);
        //'X' 아이콘으로 변경
        fabMain.setImageResource(R.drawable.ic_close);
        fabExpanded = true;
    }
    //------------------------------------------------




}//--- 끝 --------
