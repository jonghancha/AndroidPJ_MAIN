package com.android.androidpj_main.Lip;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.androidpj_main.Adapter.ViewPageAdapter;
import com.android.androidpj_main.R;
import com.google.android.material.tabs.TabLayout;

public class LipMainActivity extends AppCompatActivity {


    private TabLayout skinTab;
    private ViewPager skinViewPager;
    private ViewPageAdapter VPSkinAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lipmain);



        skinTab = findViewById(R.id.skinTab);
        skinViewPager = findViewById(R.id.skinView);

        VPSkinAdapter = new ViewPageAdapter(getSupportFragmentManager());

        //     Add Fragment
        VPSkinAdapter.AddFrmt(new Frmt_Lip_Tot(),"");   // 립전체
        VPSkinAdapter.AddFrmt(new Frmt_Lip_Tick(),""); // 립스틱
        VPSkinAdapter.AddFrmt(new Frmt_Lip_Tin(),""); // 립틴트
        VPSkinAdapter.AddFrmt(new Frmt_Lip_Rose(),""); // 립글로즈
        VPSkinAdapter.AddFrmt(new Frmt_Lip_Bam(),""); // 립케어/립밤

        skinViewPager.setAdapter(VPSkinAdapter);
        skinTab.setupWithViewPager(skinViewPager);

        mContext = this;


        skinTab.getTabAt(0).setText("전체");      // Frmt_Skin_Tot
        skinTab.getTabAt(1).setText("립스틱");  // Frmt_Lip_Tick
        skinTab.getTabAt(2).setText("립틴트");      // Frmt_Lip_Tin
        skinTab.getTabAt(3).setText("립글로즈");      // Frmt_Lip_Rose
        skinTab.getTabAt(4).setText("립케어/립밤");      // Frmt_Lip_Bam


        //--------------------------------------------------------------------------------
    }


}
