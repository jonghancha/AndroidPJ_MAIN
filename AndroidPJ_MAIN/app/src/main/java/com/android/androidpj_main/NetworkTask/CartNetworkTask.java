package com.android.androidpj_main.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.androidpj_main.Bean.Cart;
import com.android.androidpj_main.Bean.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CartNetworkTask extends AsyncTask<Integer, String, Object> {


    final static String TAG = "CartNetworkTask";
    Context context = null;
    String mAddr = null;

    String where = null;
    ArrayList<Cart> carts; // 불러와야 해서

    public CartNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.carts = new ArrayList<Cart>();
        Log.v(TAG, "Start : " + mAddr);
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG,"onCancelled()");
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        ///////////////////////////////////////////////////////////////////////////////////////
        // Date : 2020.01.11
        //
        // Description:
        //  - CartNetworkTask에서 입력,수정,검색 결과값 관리.
        //
        ///////////////////////////////////////////////////////////////////////////////////////
        String result = null;
        ///////////////////////////////////////////////////////////////////////////////////////

        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                ///////////////////////////////////////////////////////////////////////////////////////
                // Date : 2020.01.11
                //
                // Description:
                //  - 검색으로 들어온 Task는 parserSelect()로
                //  - 입력, 수정, 삭제로 들어온 Task는 parserAction()으로 구분
                //
                ///////////////////////////////////////////////////////////////////////////////////////
                if(where.equals("select")){
                    result = parserSelect(stringBuffer.toString());
                }else if (where.equals("count")){
                    result = parserCount(stringBuffer.toString());
                }else if (where.equals("getdata")){
                    parser(stringBuffer.toString());
                }else if (where.equals("noQty")){
                    parserNoqty(stringBuffer.toString());
                } else{
                    result = parserAction(stringBuffer.toString());
                }
                ///////////////////////////////////////////////////////////////////////////////////////

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();

            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        if(where.equals("getdata")){
            return carts;
        }else if (where.equals("noQty")){
          return carts;
        } else{
            return result;
        }

    }


    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.01.11
    //
    // Description:
    //  - 검색후 json parsing
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    private String parserSelect(String s){
        Log.v(TAG,"parserSelect()");
        Log.v(TAG, s);
        String check = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cart_info"));


            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                check = jsonObject1.getString("check");


                Log.v(TAG, "check : " + check);



                Log.v(TAG, "----------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return check;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    private void parser(String s){
        Log.v(TAG,"parser()");
        Log.v(TAG, s);
        String check = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cart_info"));

            carts.clear();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                int prdNo = jsonObject1.getInt("prdNo");
                String prdBrand = jsonObject1.getString("prdBrand");
                String prdName = jsonObject1.getString("prdName");
                int prdPrice = Integer.parseInt(jsonObject1.getString("prdPrice"));
                int cartQty = Integer.parseInt(jsonObject1.getString("cartQty"));
                String prdFilename = jsonObject1.getString("prdFilename");


                Log.v(TAG, "prdBrand : " + prdBrand);

                Cart cart = new Cart(prdNo, prdBrand, prdName, prdPrice, cartQty, prdFilename);

                carts.add(cart);
                Log.v(TAG, "----------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void parserNoqty(String s){
        Log.v(TAG,"parserNoqty()");
        Log.v(TAG, s);
        String check = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cart_info"));

            carts.clear();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                int prdNo = jsonObject1.getInt("prdNo");
                String prdBrand = jsonObject1.getString("prdBrand");
                String prdName = jsonObject1.getString("prdName");
                int prdPrice = Integer.parseInt(jsonObject1.getString("prdPrice"));

                String prdFilename = jsonObject1.getString("prdFilename");


                Log.v(TAG, "prdBrand : " + prdBrand);

                Cart cart = new Cart(prdNo, prdBrand, prdName, prdPrice, 1, prdFilename);

                carts.add(cart);
                Log.v(TAG, "----------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private String parserCount(String s) {

            Log.v(TAG,"parserCount()");
            Log.v(TAG, s);
            String count = null;
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("cart_info"));


                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    count = jsonObject1.getString("count");


                    Log.v(TAG, "count : " + count);



                    Log.v(TAG, "----------------------------------");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return count;
        }




    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.01.11
    //
    // Description:
    //  - 입력, 수정, 삭제후 json parsing
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    private String parserAction(String s){
        Log.v(TAG,"parserAction()");
        String returnValue = null;

        try {
            Log.v(TAG, s);

            JSONObject jsonObject = new JSONObject(s);
            returnValue = jsonObject.getString("result");
            Log.v(TAG, returnValue);

        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }
    ///////////////////////////////////////////////////////////////////////////////////////



} // ----
