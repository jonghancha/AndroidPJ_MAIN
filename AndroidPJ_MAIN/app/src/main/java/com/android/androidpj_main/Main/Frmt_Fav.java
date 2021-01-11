package com.android.androidpj_main.Main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Adapter.LikeAdapter;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.NetworkTask.LikeNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class Frmt_Fav extends Fragment {

///////////////////////////////////////////////////////////////////////////
//
// 21.01.08 세미 생성
//
///////////////////////////////////////////////////////////////////////////

    View v;
    final static String TAG = "Frmt_Fav";
    String urlAddr = null;
    ArrayList<Product> products;
    LikeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    Button ib_likebtn;
    String prdNo;

    public Frmt_Fav() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_fav,container,false);

        recyclerView = v.findViewById(R.id.fav_recycleView);

        String email = PreferenceManager.getString(getActivity(), "email"  );
        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/favSelect.jsp?user_userEmail=" + email;
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void connectGetData(){

        try {

            LikeNetworkTask likeNetworkTask = new LikeNetworkTask(getActivity(),urlAddr);

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = likeNetworkTask.execute().get();
            products = (ArrayList<Product>) object;
            //LikeAdapter.java 의 생성자를 받아온다.
            adapter = new LikeAdapter(getActivity(), R.layout.custom_like, products);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        connectGetData();
        Log.v(TAG, "onResume()");
    }



}//----------
