package com.android.androidpj_main.Main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.androidpj_main.R;

public class Frmt_Category extends Fragment {

    View v;
    final static String TAG = "Frmt_Category";

    LinearLayout view_tot, view_color;
    LinearLayout lipStick, lipTint, lipRose, lipBam;
    ImageButton cate_tot, cate_color;

    ImageView cate_warm, cate_cool;
    TextView cate_text_warm, cate_text_cool;
    TextView cate_text_stick, cate_text_tint, cate_text_rose, cate_text_bam;

    String color;
    String lip;


    public Frmt_Category() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_category,container,false);

        //
        cate_tot = v.findViewById(R.id.cate_tot);
        cate_color = v.findViewById(R.id.cate_color);

        cate_tot.setOnClickListener(cateClickListener);
        cate_color.setOnClickListener(cateClickListener);
        //

        //
        view_tot = v.findViewById(R.id.view_tot);
        view_color = v.findViewById(R.id.view_color);
        //

        //
        lipStick = v.findViewById(R.id.lipStick);
        lipTint = v.findViewById(R.id.lipTint);
        lipRose = v.findViewById(R.id.lipRose);
        lipBam = v.findViewById(R.id.lipBam);

        cate_text_stick = v.findViewById(R.id.cate_text_stick);
        cate_text_tint = v.findViewById(R.id.cate_text_tint);
        cate_text_rose = v.findViewById(R.id.cate_text_rose);
        cate_text_bam = v.findViewById(R.id.cate_text_bam);

        lipStick.setOnClickListener(lipClickListener);
        lipTint.setOnClickListener(lipClickListener);
        lipRose.setOnClickListener(lipClickListener);
        lipBam.setOnClickListener(lipClickListener);
        //

        //
        cate_warm = v.findViewById(R.id.cate_warm);
        cate_cool = v.findViewById(R.id.cate_cool);
        cate_text_warm = v.findViewById(R.id.cate_text_warm);
        cate_text_cool = v.findViewById(R.id.cate_text_cool);

        cate_warm.setOnClickListener(colorCilckListener);
        cate_cool.setOnClickListener(colorCilckListener);
        //



        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View.OnClickListener lipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lipStick:
                    lip = cate_text_stick.getText().toString();
                    Toast.makeText(getActivity(), lip + "을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lipTint:
                    lip = cate_text_tint.getText().toString();
                    Toast.makeText(getActivity(), lip + "을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lipRose:
                    lip = cate_text_rose.getText().toString();
                    Toast.makeText(getActivity(), lip + "을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lipBam:
                    lip = cate_text_bam.getText().toString();
                    Toast.makeText(getActivity(), lip + "을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    View.OnClickListener colorCilckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cate_warm:
                    color = cate_text_warm.getText().toString();
                    Toast.makeText(getActivity(), color + "을 선택하였습니다.",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cate_cool:
                    color = cate_text_cool.getText().toString();
                    Toast.makeText(getActivity(), color + "을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    View.OnClickListener cateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cate_tot:
                    view_tot.setVisibility(v.VISIBLE);
                    view_color.setVisibility(v.INVISIBLE);
                    break;
                case R.id.cate_color:
                    view_tot.setVisibility(v.INVISIBLE);
                    view_color.setVisibility(v.VISIBLE);
                    break;


            }
        }
    };

}
