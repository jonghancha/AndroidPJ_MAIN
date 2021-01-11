package com.android.androidpj_main.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.android.androidpj_main.Main.MainActivity;
import com.android.androidpj_main.NetworkTask.CartNetworkTask;
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
    String urlAddrCheck = null; // cart 에 이미 있는지 체크
    String urlAddrCount = null; // cart Qty 체크
    String urlAddrInsert = null; // 입력
    String urlAddrUpdate = null; // 수정

    String macIP,prdNo;
    Button btn_plus, btn_minus;
    EditText et_quantity;

    // 장바구니, 구매하기 버튼
    Button bottomCart, bottomBuy;
    String cartCheck, cartCount;
    String cartInsertQty;
    String cartUpdateQty;
    int cartQty;

    // 로그인한 id 받아오기
    String userEmail;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        userEmail = PreferenceManager.getString(getContext(),"email");
        Log.v(TAG, userEmail);


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

        // 장바구니, 구매
        bottomCart = getView().findViewById(R.id.btn_bottomcart);
        bottomBuy = getView().findViewById(R.id.btn_bottombuy);

        // 장바구니 체크
        urlAddrCheck = "http://" + macIP + ":8080/JSP/cart_check.jsp?userEmail=" + userEmail + "&prdNo=" + prdNo;
        // 장바구니 수량 체크
        urlAddrCount = "http://" + macIP + ":8080/JSP/cart_count.jsp?userEmail=" + userEmail + "&prdNo=" + prdNo;
        // 장바구니 입력
        urlAddrInsert = "http://" + macIP + ":8080/JSP/cart_insert.jsp?userEmail=" + userEmail + "&prdNo=" + prdNo + "&cartQty=";
        // 장바구니 수정
        urlAddrUpdate = "http://" + macIP + ":8080/JSP/cart_update.jsp?userEmail=" + userEmail + "&prdNo=" + prdNo + "&cartQty=";

        btn_plus.setOnClickListener(btnClickListener);
        btn_minus.setOnClickListener(btnClickListener);
        bottomCart.setOnClickListener(btnClickListener);
        bottomBuy.setOnClickListener(btnClickListener);

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

                case R.id.btn_bottomcart: // 장바구니 버튼
                    if (cartCheck().equals("0")){ // 장바구니에 처음으로 추가
                        Log.v(TAG, "in cartCheck() == 0");
                        // 수량만큼 장바구니에 insert
                        if (cartInsertData().equals("1")) { // Insert 성공.
                            Log.v(TAG, "in cartInsertData() == 1)");

                            new AlertDialog.Builder(getContext())
                                    .setMessage("상품이 장바구니에 담겼습니다.\n지금 확인하시겠습니까?")
                                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getActivity(), CartActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                        break;

                    }else {
                        Toast.makeText(getActivity(), "장바구니에 이미 있음", Toast.LENGTH_SHORT).show();
                        // 기존 장바구니 수량 가져오기
                        cartQty = Integer.parseInt(cartCount());
                        // 수량만큼 장바구니에 업데이트
                       // cartUpdateData().equals("1")
                            Log.v(TAG, "in cartUpdateData() == 1)");

                            // 수량을 추가하겠냐고 묻는 다이얼로그
                            new AlertDialog.Builder(getContext())
                                    .setMessage("이미 장바구니에 담긴 상품입니다.\n수량을 추가하시겠습니까?")
                                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            if (cartUpdateData().equals("1")){
                                                new AlertDialog.Builder(getContext())
                                                        .setMessage("장바구니에 상품 수량이 추가되었습니다.지금 확인하시겠습니까?")
                                                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(getActivity(), CartActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                            }
                                                        })
                                                        .show();
                                            }
                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();

                        break;

                    }

                case R.id.btn_bottombuy: // 구매하기 버튼
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



    // 해당 상품이 장바구니에 존재하는지 확인
    private String cartCheck() {
        cartCheck = "0";

        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(getContext(), urlAddrCheck, "select");
            Object obj = cartNetworkTask.execute().get();
            cartCheck = String.valueOf(obj);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v(TAG, cartCheck);
        return cartCheck;
    }

    // 장바구니에 있는 상품 Qty 체크
    private String cartCount() {
        cartCount = "0";

        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(getContext(), urlAddrCount, "count");
            Object obj = cartNetworkTask.execute().get();
            cartCount = String.valueOf(obj);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v(TAG, cartCount);
        return cartCount;
    }

    // 장바구니에 상품 Insert
    private String cartInsertData(){
        String result = null;
        cartInsertQty = String.valueOf(et_quantity.getText());
        urlAddrInsert = urlAddrInsert + cartInsertQty;
        Log.v(TAG, "cartInsertQty =" + cartInsertQty);
        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.01.11
            //
            // Description:
            //  - 입력하고 리턴값을 받음
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            CartNetworkTask networkTask = new CartNetworkTask(getActivity(), urlAddrInsert, "insert");
            ///////////////////////////////////////////////////////////////////////////////////////

            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.24
            //
            // Description:
            //  - 입력 결과 값을 받기 위해 Object로 return후에 String으로 변환 하여 사용
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            Object obj = networkTask.execute().get();
            result = (String) obj;
            ///////////////////////////////////////////////////////////////////////////////////////

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    // 장바구니에 상품 Update
    private String cartUpdateData(){
        String result = null;
        cartUpdateQty = String.valueOf(et_quantity.getText());
        urlAddrUpdate = urlAddrUpdate + cartUpdateQty;
        Log.v(TAG, "cartUpdateQty =" + cartUpdateQty);
        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.01.11
            //
            // Description:
            //  - 수정하고 리턴값을 받음
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            CartNetworkTask networkTask = new CartNetworkTask(getActivity(), urlAddrUpdate, "update");
            ///////////////////////////////////////////////////////////////////////////////////////

            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.24
            //
            // Description:
            //  - 수정 결과 값을 받기 위해 Object로 return후에 String으로 변환 하여 사용
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            Object obj = networkTask.execute().get();
            result = (String) obj;
            ///////////////////////////////////////////////////////////////////////////////////////

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


}//--------