package com.android.androidpj_main.Lip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.androidpj_main.Adapter.ViewPageAdapter;
import com.android.androidpj_main.R;
import com.google.android.material.tabs.TabLayout;


// 지은 추가 21.01.13 ***************************
public class LipMainActivity extends AppCompatActivity {


    private TabLayout lipTab;
    private ViewPager lipViewPager;
    private ViewPageAdapter VPLipAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipmain);
        Intent intent = getIntent();
        String color = intent.getStringExtra("color");
        setTitle("LIPHAE [" + color + "]");




        lipTab = findViewById(R.id.lipTab);
        lipViewPager = findViewById(R.id.lipView);

        VPLipAdapter = new ViewPageAdapter(getSupportFragmentManager());

        //     Add Fragment
        VPLipAdapter.AddFrmt(new Frmt_Lip_Tot(color),"");   // 립전체
        VPLipAdapter.AddFrmt(new Frmt_Lip_Tick(color),""); // 립스틱
        VPLipAdapter.AddFrmt(new Frmt_Lip_Tin(color),""); // 립틴트
        VPLipAdapter.AddFrmt(new Frmt_Lip_Rose(color),""); // 립글로즈
        VPLipAdapter.AddFrmt(new Frmt_Lip_Bam(color),""); // 립케어/립밤

        lipViewPager.setAdapter(VPLipAdapter);
        lipTab.setupWithViewPager(lipViewPager);

        mContext = this;


        lipTab.getTabAt(0).setText("전체");      // Frmt_Skin_Tot
        lipTab.getTabAt(1).setText("립스틱");  // Frmt_Lip_Tick
        lipTab.getTabAt(2).setText("립틴트");      // Frmt_Lip_Tin
        lipTab.getTabAt(3).setText("립글로즈");      // Frmt_Lip_Rose
        lipTab.getTabAt(4).setText("립케어/립밤");      // Frmt_Lip_Bam


        //--------------------------------------------------------------------------------
    }


}
