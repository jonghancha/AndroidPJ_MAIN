package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.DialogCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.androidpj_main.R;

public class RegisterAddress extends Activity {
    final static String TAG = "RegisterAddress";

    // PurchaseActivity 에서 값 받아오기
    String purName = null;
    String purTel = null;

    // 주문자 정보 가져오기 체크박스
    CheckBox cbGetData;

    // 주문자 정보 텍스트박스
    EditText regiAdrUserName, regiAdruserTel;

    // 도로명 주소 텍스트 박스
    EditText etAddress1, etAddress2;

    // 주소찾기 버튼
    Button btnFindAddress;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    // Final 등록하기 버튼
    Button btnAddressRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_address);

        // PurchaseActivity 에서 값 받아오기
        Intent intent = getIntent();
        purName = intent.getStringExtra("purName");
        purTel = intent.getStringExtra("purTel");


        // 타이틀
        setTitle("배송지 등록");

        // 주문자 정보 가져오기 체크박스
        cbGetData = findViewById(R.id.get_user_info);

        cbGetData.setOnCheckedChangeListener(checkedChangeListener);

        // 주문자 정보 텍스트박스
        regiAdrUserName = findViewById(R.id.regi_adr_userName);
        regiAdruserTel = findViewById(R.id.regi_adr_userTel);

        // 도로명 주소 텍스트 박스
        etAddress1 = findViewById(R.id.address1);
        etAddress2 = findViewById(R.id.address2);

        // 주소찾기 버튼
        btnFindAddress = findViewById(R.id.btn_find_address);




        //---------------------------------------------------주소API--------------------------------------------//
        btnFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterAddress.this, AddressWebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });

        // Final 등록하기 버튼
        btnAddressRegister = findViewById(R.id.btn_address_register);
        btnAddressRegister.setOnClickListener(finalRegisterListener);

    }

    /**
     * 주문자 정보와 동일하게 하는 체크박스 리스너
     */
    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (purName.trim().length() == 0){
                Toast.makeText(RegisterAddress.this, "주문자 정보를 입력하지 않으셨습니다", Toast.LENGTH_SHORT).show();
                cbGetData.setChecked(false);
            }else if (purTel.trim().length() == 0){Toast.makeText(RegisterAddress.this, "주문자 정보를 입력하지 않으셨습니다", Toast.LENGTH_SHORT).show();
            cbGetData.setChecked(false);
            } else {
                if (cbGetData.isChecked()){ // 체크되었을 때
                    regiAdrUserName.setText(purName);
                    regiAdruserTel.setText(purTel);
                }else { // 체크가 해제되었을 때
                    regiAdrUserName.setText("");
                    regiAdruserTel.setText("");
                }

            }

        }
    };

    /**
     *  도로명 api 웹뷰 결과 가져오기
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        etAddress1.setText(data);
                    }
                }
                break;
        }
    }

    View.OnClickListener finalRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (regiAdrUserName.getText().toString().trim().length() == 0){
                Toast.makeText(RegisterAddress.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                regiAdrUserName.requestFocus();
                regiAdrUserName.setCursorVisible(true);
            }else if (regiAdruserTel.getText().toString().trim().length() == 0){
                Toast.makeText(RegisterAddress.this, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                regiAdruserTel.requestFocus();
                regiAdruserTel.setCursorVisible(true);
            }else if (etAddress1.getText().toString().trim().length() == 0){
                Toast.makeText(RegisterAddress.this, "도로명 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
            }else if (etAddress2.getText().toString().trim().length() == 0){
                Toast.makeText(RegisterAddress.this, "상세 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
            }else{
                // 주문자 정보 텍스트박스
                 String resultName = String.valueOf(regiAdrUserName.getText());
                 String resultTel = String.valueOf(regiAdruserTel.getText());
                 String address1 = String.valueOf(etAddress1.getText());
                 String address2 = String.valueOf(etAddress2.getText());
                 String resultAddress = address1 + " " + address2;


                // 도로명 주소 텍스트 박스




                //버튼 또는 서브 페이지 종료 시점, 리턴 되는 시점 작성
                Intent intent = new Intent();
                intent.putExtra("ordReceiver", resultName);
                intent.putExtra("ordRcvAddress", resultAddress);
                intent.putExtra("ordRcvPhone", resultTel);
                setResult(RESULT_OK, intent);

                finish();
            }




        }
    };




}