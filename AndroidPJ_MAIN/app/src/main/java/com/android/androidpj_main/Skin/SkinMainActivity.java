package com.android.androidpj_main.Skin;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.androidpj_main.Adapter.ViewPageAdapter;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Skin.Frmt_Skin_Tot;
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
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Clean(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Mask(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Sun(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Base(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Eye(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Rip(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Body(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Hair(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Perfume(),"");
        VPSkinAdapter.AddFrmt(new Frmt_Skin_Etc(),"");

        skinViewPager.setAdapter(VPSkinAdapter);
        skinTab.setupWithViewPager(skinViewPager);

        mContext = this;


        skinTab.getTabAt(0).setText("전체");      // Frmt_Skin_Tot
        skinTab.getTabAt(1).setText("클렌징/필링");  // Frmt_Skin_Clean
        skinTab.getTabAt(2).setText("마스크/팩");      // Frmt_Skin_Mask
        skinTab.getTabAt(3).setText("선케어");      // Frmt_Skin_Sun
        skinTab.getTabAt(4).setText("베이스메이크업");      // Frmt_Skin_Base
        skinTab.getTabAt(5).setText("아이메이크업");      // Frmt_Skin_Eye
        skinTab.getTabAt(6).setText("립메이크업");      // Frmt_Skin_Rip
        skinTab.getTabAt(7).setText("바디");      // Frmt_Skin_Body
        skinTab.getTabAt(8).setText("헤어");      // Frmt_Skin_Hair
        skinTab.getTabAt(9).setText("향수");      // Frmt_Skin_Perfume
        skinTab.getTabAt(10).setText("기타");      // Frmt_Skin_Etc


        //--------------------------------------------------------------------------------
    }


}
