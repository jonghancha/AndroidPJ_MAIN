package com.android.androidpj_main.Make_Youtube;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Adapter.YoutubeAdapter;
import com.android.androidpj_main.Bean.Youtube;
import com.android.androidpj_main.NetworkTask.YoutubeNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

// 21.01.08 지은 추가 ****************************************************
public class Frmt_Ycolor extends Fragment {

    View v;
    final static String TAG = "Frmt_Ycolor";
    String urlAddr = null;
    ArrayList<Youtube> members;
    YoutubeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    public Frmt_Ycolor() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        urlAddr = "http://"+ ShareVar.macIP +":8080/JSP/youtube_color.jsp";
        v = inflater.inflate(R.layout.frmt_ycolor,container,false);
        recyclerView = v.findViewById(R.id.color_recycleView);
        YoutubeAdapter viewAdapter = new YoutubeAdapter(getContext(), R.layout.item_makeup, members);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(viewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //메소드
    private void connectGetData(){
        try {
            YoutubeNetworkTask networkTask = new YoutubeNetworkTask(getActivity(), urlAddr); //onCreate 에 urlAddr 이 선언된것이 들어옴

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = networkTask.execute().get();
            members = (ArrayList<Youtube>) object;

            //StudentAdapter.java 의 생성자를 받아온다.
            adapter = new YoutubeAdapter(getActivity(), R.layout.item_makeup, members);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    //resume 에 써주는 것이 좋다. create 에 써도 무관하지만
    @Override
    public void onResume() {
        super.onResume();
        connectGetData();
        Log.v(TAG, "onResume()");
    }

}
