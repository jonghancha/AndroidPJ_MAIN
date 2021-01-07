package com.android.androidpj_main.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.androidpj_main.R;
import com.android.androidpj_main.Lip.LipMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Frmt_Home extends Fragment {

    final static String TAG = "Frmt_Home";
    View v;


    // 21.01.07 지은 추가 ---------------
    FloatingActionButton fabMain, fabMake, fabHoney;
    Float translationY = 100f;
    Boolean isMenuOpen = false;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    //------------------------




    public Frmt_Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_home,container,false);

        //플로팅 버튼 관련
        initFabMenu();


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



//21.01.07 지은 추가------------------------
    //플로팅 버튼 관련
    private void initFabMenu() {
        fabMain = v.findViewById(R.id.fabMain);         // 숨겼다가 띄우는 fab
        fabMake = v.findViewById(R.id.fabMake);         // 메이크업 영상 fab
        fabHoney = v.findViewById(R.id.fabHoney);       // 꿀팁 fab

        fabMake.setAlpha(0f);
        fabHoney.setAlpha(0f);

        fabMake.setTranslationY(translationY);
        fabHoney.setTranslationY(translationY);

        fabMain.setOnClickListener(flCliclListener);
        fabMake.setOnClickListener(flCliclListener);
        fabHoney.setOnClickListener(flCliclListener);
    }
    // 메인 fab 클릭시 fab 띄우기
    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        fabMake.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabHoney.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();


    }
    // 메인 fab 클릭시 띄워져있는데 fab 숨기기
    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        fabMake.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabHoney.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

    }
    // 클릭했을 때 일어나는 이벤트
    View.OnClickListener flCliclListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fabMain:
                    Log.v(TAG, "onClick: fab main");
                    if (isMenuOpen) {
                        closeMenu();
                    } else {
                        openMenu();
                    }
                    break;
                case R.id.fabMake:
                    Intent intentM = new Intent(getActivity(), LipMainActivity.class);
                    startActivity(intentM);
                    Toast.makeText(getContext(), "메이크업 영상 보러가기 클릭", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "onClick: fab makeup");
                    break;
                case R.id.fabHoney:
                    Intent intentH = new Intent(getActivity(), HoneyTipActivity.class);
                    startActivity(intentH);
                    Toast.makeText(getContext(), "꿀팁 차차 보러가기 클릭", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "onClick: fab honey");
                    break;
            }

        }
    };
//--------------------------

}//-----------
