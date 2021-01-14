package com.android.androidpj_main.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.androidpj_main.Activity.BottomSheet;
import com.android.androidpj_main.Activity.CartActivity;
import com.android.androidpj_main.Activity.EmptyCartActivity;
import com.android.androidpj_main.Activity.ProductViewActivity;
import com.android.androidpj_main.Activity.SearchActivity;
import com.android.androidpj_main.Adapter.ViewPageAdapter;
import com.android.androidpj_main.NetworkTask.CartNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // 21.01.07 지은 추가 ***************************
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    Context mContext;
    // -------------------------------------------------------

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;


    //****************************************

    // 종한 광고 추가 ***************************
    // Debug tag, for logging
    static final String TAG = "Main";

    // SharedPreferences 정의
    private SharedPreferences SPreferences;

    // SharedPreferences 접근 이름, 저장 데이터 초기화
    private final String NameSPreferences = "Day";
    private String strSDFormatDay = "0";
    //****************************************

    // 21.01.14 종한 추가
    // 장바구니 빈지 여부 체크
    String macIP = ShareVar.macIP;
    // 로그인한 id 받아오기
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("LIPHAE");

        // 21.01.14 종한 추가
        // 아이디 받아오기
        userEmail = com.android.androidpj_main.Activity.PreferenceManager.getString(MainActivity.this,"email");


        //21.01.07 지은 추가 ******************************
        //검색창 눌렀을 때 탭레이아웃 올라오는거 막음
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);


        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.pageViewId);

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        //     Add Fragment
        viewPageAdapter.AddFrmt(new Frmt_Home(),"");
        viewPageAdapter.AddFrmt(new Frmt_Category(),"");
        viewPageAdapter.AddFrmt(new Frmt_Fav(),"");
        viewPageAdapter.AddFrmt(new Frmt_Mypage(),"");

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        mContext = this;

        //21.01.10 지은 수정 ------------------------------------------
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home).setText("홈");
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_cate).setText("카테고리");
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_like).setText("찜");
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_mypage).setText("마이페이지");

        // 21.01.10 지은 추가 선택되는 탭 상단 색 바꾸기 --- > 교체 예정
        tabLayout.setSelectedTabIndicatorColor(Color.RED);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        //****************************************


        // 종한 광고 추가 ***************************
        // '오늘 그만 보기' 기능을 위한 날짜 획득
        long CurrentTime = System.currentTimeMillis(); // 현재 시간을 msec 단위로 얻음
        Date TodayDate = new Date(CurrentTime); // 현재 시간 Date 변수에 저장
        SimpleDateFormat SDFormat = new SimpleDateFormat("dd");
        strSDFormatDay = SDFormat.format(TodayDate); // 'dd' 형태로 포맷 변경

        // SharedPreferences 획득
        SPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String strSPreferencesDay = SPreferences.getString(NameSPreferences, "0");
        Log.v(TAG, strSPreferencesDay);

        // 광고 띄움
        // 오늘날짜 - 이전 날짜
        if((Integer.parseInt(strSDFormatDay) - Integer.parseInt(strSPreferencesDay)) != 0)
            StartMainAdDialog();
    }

    // 초기 실행시 광고 Dialog
    public void StartMainAdDialog() {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(getSupportFragmentManager(), "MyFragment");
    }
    // *****************************************************



    // 옵션 메뉴 선언
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    // 21.01.07 지은 추가 ***************************
    // 옵션 메뉴
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "검색창 클릭", Toast.LENGTH_SHORT).show();
                Intent intentS = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentS);
                break;
            case R.id.action_cart:



                if (cartEmptyCheck().equals("0")){
                    Toast.makeText(MainActivity.this, "장바구니 비었음", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, EmptyCartActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                }


                Toast.makeText(MainActivity.this, "장바구니 클릭", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    // 뒤로가기 두번 클릭시 어플 종료
    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }
    //**********************************************


    /**
     * 장바구니가 비어있는지 여부 체크
     * @return
     */
    private String cartEmptyCheck() {
        String cartEmptyCheck = "0";
        String urlAddrCheck = "http://" + macIP + ":8080/JSP/cart_empty_check.jsp?userEmail=" + userEmail;
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(MainActivity.this, urlAddrCheck, "select");
            Object obj = cartNetworkTask.execute().get();
            cartEmptyCheck = String.valueOf(obj);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v(TAG, cartEmptyCheck);
        return cartEmptyCheck;
    }


}
