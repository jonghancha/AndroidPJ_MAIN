package com.android.androidpj_main.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.androidpj_main.Bean.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

///////////////////////////////////////////////////////////////////////////
//
// 21.01.14 세미 생성
//
///////////////////////////////////////////////////////////////////////////

public class OrderNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "OrderNetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Order> orders; // 불러와야 해서

    //Constructor
    public OrderNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.orders = new ArrayList<Order>();    //직접 침 : 이유는 꼭 쓸 필요성은 없지만 arraylist를 사용하기 위해 생성해줌
        Log.v(TAG, "Start : "+ mAddr);
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Data Fetch");
        progressDialog.setMessage("Get ....");
        progressDialog.show();
    }


    //String 의 약점은 +=
    //String 보다 StringBuffer 가 더 빠르다
    //StringBuffer 와 StringBuild 와 같음
    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;   //  inputStreamReader 로 써도 무관하지만 더 느리기 때문에 BufferReader

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000); //10초를 줌 (연결하는 시간=connect 시간)

            //위에서 준 connection 이 연결이 되었다면
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);   // 한번에 불러온다


                while (true){
                    String strline = bufferedReader.readLine();
                    if (strline ==  null)break;
                    stringBuffer.append(strline + "\n");
                }
                parser(stringBuffer.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return orders;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.v(TAG, "onProgressUpdate()");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled()");
        super.onCancelled();
    }

    private void parser(String s){
        Log.v(TAG, "Parser()");
        try {
            Log.v(TAG, "order s값:::::" + s);
            // 배열이기 때문에 [] 이렇게 시작
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("order_select"));
            // object 가 읽어줌
            //students_info는 테이블 명이라고 생각할 것

            orders.clear();

            // object 별로 불러오는 것 {이 안의 묶음}
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                int ordNo = jsonObject1.getInt("ordNo");
                String ordDate = jsonObject1.getString("ordDate");
                String ordDelivery = jsonObject1.getString("ordDelivery");
                String prdName = jsonObject1.getString("prdName");
                int prdPrice = jsonObject1.getInt("prdPrice");
                String prdFilename = jsonObject1.getString("prdFileName");


                Order order = new Order(ordNo, ordDate, ordDelivery, prdName, prdPrice, prdFilename);

                orders.add(order);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}//-- Buffer 에 만들고 ArrayList 를 만듦
