package com.android.androidpj_main.Skin;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.androidpj_main.Adapter.ViewPageAdapter;
import com.android.androidpj_main.R;
import com.google.android.material.tabs.TabLayout;

public class SkinMainActivity extends AppCompatActivity {


    private TabLayout skinTab;
    private ViewPager skinViewPager;
    private ViewPageAdapter VPSkinAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skinmain);



        skinTab = (TabLayout) findViewById(R.id.skinTab);
        skinViewPager = (ViewPager) findViewById(R.id.skinView);

        VPSkinAdapter = new ViewPageAdapter(getSupportFragmentManager());

        //     Add Fragment
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Tot(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_ST(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_RE(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_EAS(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_C(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_M(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_P(),"");

        skinViewPager.setAdapter(VPSkinAdapter);
        skinTab.setupWithViewPager(skinViewPager);

        mContext = this;


        skinTab.getTabAt(0).setText("전체");      // Frmt_Skin_Tot
        skinTab.getTabAt(1).setText("스킨/토너");  // Frmt_Skin_ST
        skinTab.getTabAt(2).setText("로션/에멀젼");      // Frmt_Skin_RE
        skinTab.getTabAt(3).setText("에센스/앰플/세럼");      // Frmt_Skin_EAS
        skinTab.getTabAt(4).setText("크림");      // Frmt_Skin_C
        skinTab.getTabAt(5).setText("미스트");      // Frmt_Skin_M
        skinTab.getTabAt(6).setText("팩");      // Frmt_Skin_P


        //--------------------------------------------------------------------------------
    }


}
