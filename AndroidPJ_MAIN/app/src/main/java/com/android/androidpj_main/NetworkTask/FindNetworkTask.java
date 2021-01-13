package com.android.androidpj_main.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.androidpj_main.Bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FindNetworkTask extends AsyncTask<Integer, String, Object> {


    //이강후

    final static  String TAG = "FindActivity";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<User> members;

    //  - NetworkTask를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    String where = null;

    //constructor
    public FindNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.members = new ArrayList<User>();
        this.where = where;
        Log.v(TAG,"Start : " + mAddr);
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("Get ....");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        ///////////////////////////////////////////////////////////////////////////////////////
        //
        // Description:
        //  - NetworkTask에서 입력,수정,검색 결과값 관리.
        //
        ///////////////////////////////////////////////////////////////////////////////////////
        String result = null;
        String result2 = null;
        ///////////////////////////////////////////////////////////////////////////////////////

        try {
            //생성자에서 주소 가져옴.
            URL url = new URL(mAddr); //주소.어디가서 가져와라.
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream(); //데이터 가져오기.
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader); //버퍼에 차곡차곡 담는다.

                while (true){
                    String strline = bufferedReader.readLine(); //데이터를 라인 단위로 읽을수있음.
                    if(strline == null) break;  //읽을게 없으면 빠져나간다.
                    stringBuffer.append(strline + "\n"); //stringBuffer.append: 문자열 추가하기.
                }
                ///////////////////////////////////////////////////////////////////////////////////////
                // Date : 2020.12.25
                //
                // Description:
                //  - 검색으로 들어온 Task는 parserSelect()로
                //  - 입력, 수정, 삭제로 들어온 Task는 parserAction()으로 구분
                //
                ///////////////////////////////////////////////////////////////////////////////////////
                if(where.equals("findId")){
                    result = parserFindID(stringBuffer.toString());
                    Log.v(TAG, "result");
                }else if(where.equals("findPw")){
                    result2 = parserFindPw(stringBuffer.toString());
                    Log.v(TAG, "result2");
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
        ///////////////////////////////////////////////////////////////////////////////////////
        // Date : 2020.12.25
        //
        // Description:
        //  - 검색으로 들어온 Task는 members를 return
        //  - 입력, 수정, 삭제로 들어온 Task는 result를 return
        //
        ///////////////////////////////////////////////////////////////////////////////////////
        if(where.equals("findId")){
            return result;
        }else {
            return result2;
        }
        ///////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();

    }

    @Override
    protected void onCancelled() {
        Log.v(TAG,"onCancelled()");
        super.onCancelled();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - 검색후 json parsing
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    private String parserFindID(String s){
        Log.v(TAG,"ParserFindID");
        String returnValue = null;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));

            for(int i =0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                returnValue = jsonObject1.getString("result");

                Log.v(TAG, "result : " + returnValue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  returnValue;
    }

    //---------------------------------------------------------------------------------------
    private String parserFindPw (String s){
        Log.v(TAG,"parseFindPw");
        String returnValue2 = null;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));

            for(int i =0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                returnValue2 = jsonObject1.getString("result2");

                Log.v(TAG, "result2 : " + returnValue2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  returnValue2;

    }
    ///////////////////////////////////////////////////////////////////////////////////////



}//-----


