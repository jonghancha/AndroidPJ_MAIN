package com.android.androidpj_main.Main;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Adapter.HomeAdapter;
import com.android.androidpj_main.Activity.HoneyTipActivity;
import com.android.androidpj_main.Adapter.SliderAdapter;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.Make_Youtube.YoutubeActivity;
import com.android.androidpj_main.Model.SliderItem;
import com.android.androidpj_main.NetworkTask.HomeNetworkTask;
import com.android.androidpj_main.NetworkTask.UserColorNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;
import com.android.androidpj_main.Test.TestMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class Frmt_Home extends Fragment {

    final static String TAG = "Frmt_Home";
    View v;


    // 21.01.07 지은 추가 ***************************
    //boolean 플래그는 main FAB가 열린 상태인지 닫힌 상태인지 알 수 있음.
    private boolean fabExpanded = false;
    Button gotest;
    FloatingActionButton fabMain;   // 메안 플로팅 버튼
    FloatingActionButton fabMake, fab_honey;

    //Linear layout holding the Make submenu
    LinearLayout layoutFabMake;
    //Linear layout holding the Honey submenu
    LinearLayout layoutFabHoney;

    // 21.01.09 지은 추가 ---------------
    SliderView sliderView;
    //****************************************

    // 21.01.13 세미 추가 ***************************
    RecyclerView recyclerView;
    String urlAddr = null;
    String result;
    ArrayList<Product> products;
    HomeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    TextView home_usercolor;
    //*********************************************


    public Frmt_Home() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_home,container,false);

        String email = PreferenceManager.getString(getActivity(), "email");
        // recyclerView 연결
        recyclerView = v.findViewById(R.id.home_recycleView);

        // 지은 추가 21.01.08 ***************************
        gotest = v.findViewById(R.id.btn_test);
        fabMain = v.findViewById(R.id.fabMain);
        fabMake = v.findViewById(R.id.fabMake);
        fab_honey = v.findViewById(R.id.fab_honey);
        layoutFabMake = v.findViewById(R.id.layoutFabMake);
        layoutFabHoney = v.findViewById(R.id.layoutFabHoney);

        gotest.setOnClickListener(homeBtnClickListener);
        fabMain.setOnClickListener(homeBtnClickListener);
        fabMake.setOnClickListener(homeBtnClickListener);
        fab_honey.setOnClickListener(homeBtnClickListener);


        //플로팅 버튼 시작할때 숨기기
        closeSubMenusFab();
        //------------------------------------------------

        //지은 21.01.09------------------------------------
        sliderView = v.findViewById(R.id.imageSlider);

        MainBanner();
        //**********************************************

        // 세미 추가 21.01.13 ***************************

        home_usercolor = v.findViewById(R.id.home_usercolor);

        // 로그인한 아이디 userColor 판단.
        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/homeSelect.jsp?user_userEmail=" + email;

        // 판단한 결과로 추천상품 띄워주기
         result = userColorCheck();


        // 로그인한 user의 userColor가 웜톤
         if(result.equals("웜톤")){
             // productColor가 웜톤인 상품 5개 불러오기
             urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/colorSelect.jsp?userColor=" + result;
             connectGetData();
             home_usercolor.setText(result);

        // 로그인한 user의 userColor가 쿨톤
         }else if (result.equals("쿨톤")){
             urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/colorSelect.jsp?userColor=" + result;
             connectGetData();
             home_usercolor.setText(result);

             // 로그인한 user의 userColor가 null
         }else{
             urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/colorrandSelect.jsp";
             connectGetData();
         }




        //**********************************************

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //지은 추가 21.01.09 *****************************************
    // 배너에 이용되는 메소드 분리-------
    public void MainBanner(){
        SliderAdapter adapter = new SliderAdapter(getContext());
        adapter.addItem(new SliderItem("","https://img1.daumcdn.net/thumb/R720x0/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fliveboard%2Fyookkystudio%2F25bbc5bf0ea74c98a9e0fa244e9dc7b1.JPG"));
        adapter.addItem(new SliderItem("","https://cdn.imweb.me/upload/S20170506590dbaf15cf09/5c92e45e4e8e5.gif"));
        adapter.addItem(new SliderItem("","https://post-phinf.pstatic.net/MjAxNzA3MjhfMjY5/MDAxNTAxMjA1MzUyOTYz.wtBPmVQGbJp38EiyeZu5AzLnVb1jnqxIxLfV-hYO6gAg.TEQEeHgDem8EhaJczzIjP_B2QE7dv76aGC6p0DrYHAsg.JPEG/170728_%EB%A6%BD%EC%BB%AC%EB%9F%AC%EC%B0%BE%EA%B8%B0_%ED%94%84%EB%A1%9C%EB%AA%A8%EC%85%98_APP_02.jpg?type=w1200"));

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });    }


    // 지은 추가 21.01.09-------------------------------
    // 지은 home 쪽에서 쓰이는 버튼 기능 수정
    View.OnClickListener homeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_test:
                    Intent intent = new Intent(getActivity(), TestMainActivity.class);
                    startActivity(intent);
                    break;

                // 플로팅 버튼 관련 -------------------------
                case R.id.fabMain:
                    if (fabExpanded == true) {
                        closeSubMenusFab();
                    } else {
                        openSubMenusFab();
                    }
                    break;

                case R.id.fab_honey:
                    Toast.makeText(getContext(), "꿀팁 차차", Toast.LENGTH_SHORT).show();
                    Intent Tintent = new Intent(getActivity(), HoneyTipActivity.class);
                    startActivity(Tintent);
                    break;

                case R.id.fabMake:
                    Toast.makeText(getContext(), "메이크업 영상", Toast.LENGTH_SHORT).show();
                    Intent Mintent = new Intent(getActivity(), YoutubeActivity.class);
                    startActivity(Mintent);
                    break;
                // 플로팅 버튼 관련 -------------------------
            }
        }
    };//------------------------------------------------


    // 지은 추가 21.01.08-------------------------------
    // 플로팅 서브메뉴 숨기기
    private void closeSubMenusFab() {
        layoutFabMake.setVisibility(View.INVISIBLE);
        layoutFabHoney.setVisibility(View.INVISIBLE);
        fabMain.setImageResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    // 플로팅 서브메뉴 나타내기
    private void openSubMenusFab() {
        layoutFabMake.setVisibility(View.VISIBLE);
        layoutFabHoney.setVisibility(View.VISIBLE);
        //'X' 아이콘으로 변경
        fabMain.setImageResource(R.drawable.ic_close);
        fabExpanded = true;
    }
    //**********************************************


    // 21.01.13 세미 **********************************
    // userColor 판단하기
    public String userColorCheck(){

        String result = null;

        try {

            UserColorNetworkTask userColorNetworkTask = new UserColorNetworkTask(getActivity(), urlAddr);

            Object obj = userColorNetworkTask.execute().get();
            result = (String) obj;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // networktask
    public void connectGetData(){

        try {

            HomeNetworkTask homeNetworkTask = new HomeNetworkTask(getActivity(),urlAddr);

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = homeNetworkTask.execute().get();
            products = (ArrayList<Product>) object;
            //LikeAdapter.java 의 생성자를 받아온다.
            adapter = new HomeAdapter(getActivity(), R.layout.custom_home, products);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}//— 끝 ————