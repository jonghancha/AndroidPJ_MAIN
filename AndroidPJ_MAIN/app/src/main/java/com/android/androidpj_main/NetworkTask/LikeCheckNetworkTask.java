package com.android.androidpj_main.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.androidpj_main.Bean.Product;

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
// 21.01.08 세미 생성
//
///////////////////////////////////////////////////////////////////////////

public class LikeCheckNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "LikeCheckNetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Product> products; // 불러와야 해서
    int result = 0;

    //Constructor
    public LikeCheckNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.products = new ArrayList<Product>();    //직접 침 : 이유는 꼭 쓸 필요성은 없지만 arraylist를 사용하기 위해 생성해줌
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
                result = parser(stringBuffer.toString());
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
        return result;
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

    private int parser(String s){
        Log.v(TAG, "likeCheckParser()");
        int returnValue = 0;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("like_select"));

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                returnValue = jsonObject1.getInt("result");

                Log.v(TAG, "likereturnValue : " + returnValue);
                //products.add(product);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  returnValue;
    }

}//-- Buffer 에 만들고 ArrayList 를 만듦
