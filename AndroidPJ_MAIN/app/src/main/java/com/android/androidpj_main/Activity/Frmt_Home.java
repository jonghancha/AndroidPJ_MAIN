package com.android.androidpj_main.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

    //boolean flag to know if main FAB is in open or closed state.
    private boolean fabExpanded = false;
    private FloatingActionButton fabMain;   // 메안 플로팅 버튼
    private FloatingActionButton fabMake, fab_honey;

    //Linear layout holding the Save submenu
    private LinearLayout layoutFabSave;
    //Linear layout holding the Edit submenu
    private LinearLayout layoutFabEdit;
    //------------------------

    Button gotest;




    public Frmt_Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_home,container,false);

        //플로팅 버튼 관련 - xml 아이디 선언
        initUI();

        gotest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestMainActivity.class);
                startActivity(intent);
            }
        });

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true) {
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        fab_honey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "꿀팁 차차", Toast.LENGTH_SHORT).show();
            }
        });
        fabMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "메이크업 영상", Toast.LENGTH_SHORT).show();

            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 버튼선언 - xml 과의
    private void initUI() {
        gotest = v.findViewById(R.id.btn_test);
        fabMain = v.findViewById(R.id.fabMain);
        fabMake = v.findViewById(R.id.fabMake);
        fab_honey = v.findViewById(R.id.fab_honey);
        layoutFabSave = v.findViewById(R.id.layoutFabSave);
        layoutFabEdit = v.findViewById(R.id.layoutFabEdit);
    }

    //closes FAB submenus
    private void closeSubMenusFab() {
        layoutFabSave.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        fabMain.setImageResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab() {
        layoutFabSave.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        //'X' 아이콘으로 변경
        fabMain.setImageResource(R.drawable.ic_close);
        fabExpanded = true;
    }



}//-----------
