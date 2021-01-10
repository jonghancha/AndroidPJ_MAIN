package com.android.androidpj_main.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.androidpj_main.NetworkTask.SpinnerNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheet extends BottomSheetDialogFragment {

    ///////////////////////////////////////////////////////////////////////////
    //
    // 21.01.09 세미 생성
    // BottomSheet에 Spinner 연결, Spinner DB값 불러오기.
    //
    ///////////////////////////////////////////////////////////////////////////

    final static String TAG = "BottomSheet";
    ArrayAdapter adapter = null;
    Spinner spinner = null;
    ArrayList<String> spinnerList;
    String urlAddr = null;
    String macIP,prdNo;
    Button btn_plus, btn_minus;
    EditText et_quantity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.activity_bottom_sheet, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 번들로 넘겨준 값 가져오기
//        Bundle bundle = getArguments();
//        prdNo = bundle.getString("prdNo");
//        Log.v(TAG, "bottomsheet::::::::" + prdNo);

        // Intent로 값 받아오기
        Intent intent = getActivity().getIntent();
        prdNo = String.valueOf(intent.getIntExtra("prdNo", 0));
        Log.v(TAG, "prdNOBOTTOm:::::" + prdNo);

        macIP = ShareVar.macIP;
        urlAddr = "http://" + macIP + ":8080/JSP/spinner_option_list.jsp?prdNo=" + prdNo;
        spinner = getView().findViewById(R.id.sp_bottom);

        connectGetData();
        // 수량
        btn_plus = getView().findViewById(R.id.btn_plus);
        btn_minus = getView().findViewById(R.id.btn_minus);
        et_quantity = getView().findViewById(R.id.et_quantity);

        btn_plus.setOnClickListener(btnClickListener);
        btn_minus.setOnClickListener(btnClickListener);

//        getView().findViewById(R.id.button_bottom_sheet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
    }

    //--------------------------

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int et_quan = Integer.parseInt(et_quantity.getText().toString());
            switch (v.getId()){
                case R.id.btn_plus: // 플러스 버튼
                    et_quan = et_quan + 1;
                   et_quantity.setText(String.valueOf(et_quan));
                   Log.v(TAG, "증가값::::" + et_quan);
                   break;

                case  R.id.btn_minus:  // 마이너스 버튼
                    if (et_quan == 1){
                        Toast.makeText(getContext(), "최소 주문수랑은 1개 입니다.", Toast.LENGTH_SHORT).show();
                    }else {
                    et_quan = et_quan - 1;
                    }
                    et_quantity.setText(String.valueOf(et_quan));
                    Log.v(TAG, "감소값::::" + et_quan);
                   break;

            }
        }
    };



    private void  connectGetData(){
        try {
            SpinnerNetworkTask networkTask = new SpinnerNetworkTask(getContext(), urlAddr,"select");
            Object obj = networkTask.execute().get();
            spinnerList = (ArrayList<String>) obj;
            Log.v(TAG, "spinnerList.size() : " + spinnerList.size());

            adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,spinnerList);
            spinner.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}//--------