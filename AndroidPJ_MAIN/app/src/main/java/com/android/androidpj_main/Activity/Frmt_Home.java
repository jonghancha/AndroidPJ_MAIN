package com.android.androidpj_main.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.androidpj_main.R;
import com.android.androidpj_main.Skin.SkinMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Frmt_Home extends Fragment {

    View v;
    final static String TAG = "Frmt_Home";
    Button btn_skin;


    public Frmt_Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_home,container,false);


        FloatingActionButton floatingActionButton = v.findViewById(R.id.home_tip);
        floatingActionButton.setOnClickListener(floatCliclListener);

        btn_skin = v.findViewById(R.id.btn_skin);
        btn_skin.setOnClickListener(gomainClick);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // 플로팅 버튼( 새로운 연락처 추가하는 액티비티로 옮김 )
    View.OnClickListener floatCliclListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),HoneyTipActivity.class);
            startActivity(intent);
        }
    };

    // SkinMainActivity.java로 이동
    View.OnClickListener gomainClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SkinMainActivity.class);
            startActivity(intent);
        }
    };

}//-----------
