package com.android.androidpj_main.Make_Youtube;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.androidpj_main.Adapter.YouPageAdapter;
import com.android.androidpj_main.R;
import com.google.android.material.tabs.TabLayout;

// 21.01.08 지은 추가 ****************************************************
public class YoutubeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private YouPageAdapter viewPageAdapter;

    // 21.01.13 지은 추가 ****************************************************
    LinearLayout goyou, goinsta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        tabLayout = findViewById(R.id.you_tablayout);
        viewPager = findViewById(R.id.you_viewpager);

        // 21.01.13 지은 추가 ****************************************************
        goyou = findViewById(R.id.goyou);
        goinsta = findViewById(R.id.goinsta);

        goyou.setOnClickListener(goUrlClickListener);
        goinsta.setOnClickListener(goUrlClickListener);
        //****************************************************

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


    // 21.01.13 지은 추가 ****************************************************
    // 이사배 유튜브 채널 + 인스타 계정으로 이동
    View.OnClickListener goUrlClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.goyou:
                    Toast.makeText(YoutubeActivity.this, "이사배 유튜브 채널로 이동", Toast.LENGTH_SHORT).show();
                    Intent intent_you = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC9kmlDcqksaOnCkC_qzGacA"));
                    startActivity(intent_you);
                    break;

                case R.id.goinsta:
                    Toast.makeText(YoutubeActivity.this, "이사배 인스타 계정으로 이동", Toast.LENGTH_SHORT).show();
                    Intent intent_insta = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/risabae_art/?hl=ko"));
                    startActivity(intent_insta);
                    break;
            }
        }
    };
}//---- 끝 ----
