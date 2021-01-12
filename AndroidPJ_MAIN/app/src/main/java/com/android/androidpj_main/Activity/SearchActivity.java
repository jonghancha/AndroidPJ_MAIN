package com.android.androidpj_main.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Adapter.SearchAdapter;
import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.NetworkTask.LikeNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

// 제품 검색 (한줄-가로)
public class SearchActivity extends AppCompatActivity {

    // 지은 추가 21.01.10 ***************************

    final static String TAG = "SearchActivity";
    String urlAddr = null;
    ArrayList<Product> products;
    SearchAdapter searchAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView search_recyclerView;
//    GridLayoutManager gridLayoutManager;
    // 검색창
    EditText search_EdT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_recyclerView = findViewById(R.id.search_recycleView);
        search_EdT = findViewById(R.id.search_ET);
        search_EdT.addTextChangedListener(textChangedListener);



        connectGetData();
    }



    //메소드 = 로그인한 아이디값에 저장된 연락처 띄워주는
    private void connectGetData(){
        try {
            LikeNetworkTask networkTask = new LikeNetworkTask(SearchActivity.this, urlAddr); //onCreate 에 urlAddr 이 선언된것이 들어옴

            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
            Object object = networkTask.execute().get();
//            gridLayoutManager = new GridLayoutManager(this, 2);
            products = (ArrayList<Product>) object;

            searchAdapter = new SearchAdapter(SearchActivity.this, R.layout.item_search, products);
            search_recyclerView.setAdapter(searchAdapter);
            search_recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(SearchActivity.this);
            search_recyclerView.setLayoutManager(layoutManager);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //-------------------------------------------------------
    //  textChanged 시 검색기능 쓰기
    TextWatcher textChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // 텍스트가 변할때마다 조건검색이 실행 됩니다.
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            // 텍스트가 변할때마다 urlAddr에 덮어씌워져서 그때마다 그냥 초기화시켜줌

            urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/SearchList.jsp?search_text=";

            String searchText = search_EdT.getText().toString().trim();
            urlAddr = urlAddr + searchText;
            connectGetData();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

//-------------------------------------------------------


    //입력 되어도 리스트에 실시간으로 추가 되게 만들어줌(유지)
    @Override
    public void onResume() {
        super.onResume();
        connectGetData();
        Log.v(TAG, "onResume()");
    }

}