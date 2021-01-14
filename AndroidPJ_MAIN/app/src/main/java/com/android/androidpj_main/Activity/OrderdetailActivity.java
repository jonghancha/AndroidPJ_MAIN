package com.android.androidpj_main.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.Adapter.OrderAdapter;
import com.android.androidpj_main.Bean.Order;
import com.android.androidpj_main.NetworkTask.OrderNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class OrderdetailActivity extends AppCompatActivity {

///////////////////////////////////////////////////////////////////////////
//
// 21.01.14 세미 생성
//
///////////////////////////////////////////////////////////////////////////

    final static String TAG = "OrderdetailActivity";
    String urlAddr = null;
    ArrayList<Order> data;
    OrderAdapter adapter;
    ListView listView;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);

        // listview 연결
        listView = findViewById(R.id.lv_orderdetail);

        email = PreferenceManager.getString(OrderdetailActivity.this, "email");
        urlAddr = urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/orderSelect.jsp?user_userEmail=" + email;
        connectGetData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    private void connectGetData(){
        try {

            OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderdetailActivity.this, urlAddr);
            ///////////////////////////////////////////////////////////////////////////////////////

            Object obj = orderNetworkTask.execute().get();
            data = (ArrayList<Order>) obj;
            Log.v(TAG, "data.size() : " + data.size());

            adapter = new OrderAdapter(OrderdetailActivity.this, R.layout.custom_orderdetail, data);
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}