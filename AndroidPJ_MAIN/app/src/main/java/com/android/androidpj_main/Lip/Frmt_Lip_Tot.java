package com.android.androidpj_main.Lip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.androidpj_main.R;

public class Frmt_Lip_Tot extends Fragment {

    View v;
    final static String TAG = "Frmt_Skin_Tot";


    public Frmt_Lip_Tot() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_lip_tot,container,false);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}