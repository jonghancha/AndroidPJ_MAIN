package com.android.androidpj_main.Make_Youtube;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.androidpj_main.Adapter.YouPageAdapter;
import com.android.androidpj_main.R;
import com.google.android.material.tabs.TabLayout;

public class YoutubeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private YouPageAdapter viewPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        tabLayout = findViewById(R.id.you_tablayout);
        viewPager = findViewById(R.id.you_viewpager);

        viewPageAdapter = new YouPageAdapter(getSupportFragmentManager());

   //     Add Fragment
        viewPageAdapter.AddFrmt(new Frmt_Ybasic(),"");    // 연예인
        viewPageAdapter.AddFrmt(new Frmt_Ystar(),"");    // 연예인
        viewPageAdapter.AddFrmt(new Frmt_Yseason(),"");  // 계절
        viewPageAdapter.AddFrmt(new Frmt_Ycolor(),"");   // 색깔
        viewPageAdapter.AddFrmt(new Frmt_Yholiday(),"");   // 공휴일

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("기초 메이크업");
        tabLayout.getTabAt(1).setText("연예인 커버");
        tabLayout.getTabAt(2).setText("계절별");
        tabLayout.getTabAt(3).setText("색깔별");
        tabLayout.getTabAt(4).setText("특별한 날");

        //Remove ActionBar Shadow
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }
}
