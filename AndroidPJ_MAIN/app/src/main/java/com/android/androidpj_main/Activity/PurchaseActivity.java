package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.androidpj_main.Adapter.CartAdapter;
import com.android.androidpj_main.Adapter.PurchaseAdapter;
import com.android.androidpj_main.Bean.Cart;
import com.android.androidpj_main.Bean.User;
import com.android.androidpj_main.NetworkTask.CartNetworkTask;
import com.android.androidpj_main.NetworkTask.UserNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {

    final static String TAG = "PurchaseActivity";
    // intent 로 받을 값들
    ArrayList<Cart> getCartData;
    String totalPrice;

    String urlAddr = null;
    ArrayList<Cart> cart;
    PurchaseAdapter purchaseAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView purchaseRecyclerView;

    String macIP = ShareVar.macIP;
    // 로그인한 id 받아오기
    String userEmail;

    // 유저 정보 가져올 때 필요한 변수
    ArrayList<User> users;

    // 로그인한 아이디에 대한 정보 띄움
    String urlAddr_user = null;

    // 주문자 정보
    EditText purchaseUserName, purchaseUserTel, purchaseUserEmail;

    // 배송지 등록
    Button registerAddress;

    private static final int GET_ADDRESS_DATA = 10101;

    // 배송 요청 사항 스피너
    ArrayAdapter<CharSequence> adapter = null;
    Spinner purchaseSpinner = null;

    // 에딧텍스트 만들어주기 위해 아이디 받아옴
    LinearLayout ll;
    EditText et;


    /////////////////////////////////////////////
    // 결제 수단
    /////////////////////////////////////////////
    Button payCard, payBank;
    EditText card1, card2, card3, card4, card5;
    LinearLayout llCard, llBank;



    // 총 결제금액 넣어주기
    TextView purchaseTotalPrice;

    // 결제버튼
    Button btnBuy;

    // orderinfo 입력할 데이터
    String ordReceiver, ordRcvAddress, ordRcvPhone, orderRequest, ordPay, ordCardNo, ordCardPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        setTitle("주문/결제");
        // 유저 아이디 받기
        userEmail = PreferenceManager.getString(PurchaseActivity.this,"email");

        // 주문자 정보 넣기
        purchaseUserName = findViewById(R.id.purchase_userName);
        purchaseUserTel = findViewById(R.id.purchase_userTel);
        purchaseUserEmail = findViewById(R.id.purchase_userEmail);

        // 배송지 등록
        registerAddress = findViewById(R.id.register_address);
        registerAddress.setOnClickListener(registerBtnListener);




        // 카트에서 선택한 값 받아오기
        Intent intent = getIntent();
        getCartData = (ArrayList<Cart>) intent.getSerializableExtra("cartData");
        totalPrice = intent.getStringExtra("totalPrice");

        // 배송 요청 사항 스피너
        adapter = ArrayAdapter.createFromResource(PurchaseActivity.this, R.array.request, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        purchaseSpinner = findViewById(R.id.purchase_spinner);
        purchaseSpinner.setAdapter(adapter);

        purchaseSpinner.setOnItemSelectedListener(spinnerClickListener);

        // 에딧텍스트 만들어주기 위해 아이디 받아옴
        ll = findViewById(R.id.ll);

        purchaseRecyclerView = findViewById(R.id.purchase_recycleView);



        /////////////////////////////////////////////
        // 결제 수단
        /////////////////////////////////////////////
        payCard = findViewById(R.id.pay_card);
        payBank = findViewById(R.id.pay_bank);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);

        llCard = findViewById(R.id.ll_card);
        llBank = findViewById(R.id.ll_bank);

        // 버튼 클릭 시 frame 변경
        payCard.setOnClickListener(paymentChoice);
        payBank.setOnClickListener(paymentChoice);

        // 카드번호 4개씩 자동이동
        card1.addTextChangedListener(addTextChangedListener);
        card2.addTextChangedListener(addTextChangedListener);
        card3.addTextChangedListener(addTextChangedListener);
        card4.addTextChangedListener(addTextChangedListener);

        // EditText에 입력시 자릿수 제한.
        card1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        card2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        card3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        card4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        card5.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        // 총 결제금액 넣어주기
        purchaseTotalPrice = findViewById(R.id.purchase_totalprice);

        // 결제버튼
        btnBuy = findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(buyButtonClickListener);


        }

    // 상품을 띄우는
    private void connectGetCart() {

        purchaseAdapter = new PurchaseAdapter(PurchaseActivity.this, R.layout.item_purchase, getCartData);
        purchaseRecyclerView.setAdapter(purchaseAdapter);
        purchaseRecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(PurchaseActivity.this);
        purchaseRecyclerView.setLayoutManager(layoutManager);


    }

    //입력 되어도 리스트에 실시간으로 추가 되게 만들어줌(유지)
    @Override
    public void onResume() {
        super.onResume();
        getUserDate();
        connectGetCart();
        purchaseTotalPrice.setText(totalPrice);
        Log.v(TAG, "onResume()");
    }

    /**
     * 배송지 등록 버튼 리스너
     */
    View.OnClickListener registerBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PurchaseActivity.this, RegisterAddress.class);
            intent.putExtra("purName", String.valueOf(purchaseUserName.getText()));
            intent.putExtra("purTel", String.valueOf(purchaseUserTel.getText()));
            Log.v(TAG, "배송지 등록으로 가는 값들은 " + purchaseUserName.getText() +  purchaseUserTel.getText());
            startActivityForResult(intent, GET_ADDRESS_DATA);
        }
    };

    /**
     * 배송지 등록 후 배송정보 가져오기
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_ADDRESS_DATA){
            Toast.makeText(getApplicationContext(),
                    "요청코드 : " + requestCode + " / 결과코드 : " + resultCode, Toast.LENGTH_LONG).show();
            if (resultCode == RESULT_OK) {
                ordReceiver = data.getExtras().getString("ordReceiver");
                ordRcvAddress = data.getExtras().getString("ordRcvAddress");
                ordRcvPhone = data.getExtras().getString("ordRcvPhone");
            }
        }
    }

    /**
     * 스피너 클릭 리스너
     */
    AdapterView.OnItemSelectedListener spinnerClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (purchaseSpinner.getSelectedItemPosition() == 3){
                et = new EditText(getApplicationContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



                et.setLayoutParams(p);
                et.setHint("요청사항을 입력하세요");
                et.setSingleLine();
                et.setBackground(ContextCompat.getDrawable(PurchaseActivity.this,R.drawable.border));


                ll.addView(et);
                Log.v("Pur", String.valueOf(et.getText()));
            }else {
                ll.removeAllViews();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * 유저 정보 가져오기 메소드
     */
    private void getUserDate(){
        // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
        urlAddr_user = "http://" + ShareVar.macIP + ":8080/JSP/mySelect.jsp?userEmail=" + userEmail;
        try {
            UserNetworkTask networkTask = new UserNetworkTask(PurchaseActivity.this, urlAddr_user);
            Object obj = networkTask.execute().get();
            users = (ArrayList<User>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }

        // 로그인한 사용자 정보 입력하기
        purchaseUserName.setText(users.get(0).getUserName());
        purchaseUserTel.setText(users.get(0).getUserTel());
        purchaseUserEmail.setText(users.get(0).getUserEmail());

    }


    /**
     * 결제수단 선택 메소드
     */
    View.OnClickListener paymentChoice = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.pay_card:
                    payCard.setBackground(ContextCompat.getDrawable(PurchaseActivity.this,R.drawable.border));
                    payCard.setTextColor(Color.parseColor("#000000"));
                    payBank.setBackground(ContextCompat.getDrawable(PurchaseActivity.this,R.drawable.border_lignt));
                    payBank.setTextColor(Color.parseColor("#939393"));
                    llCard.setVisibility(View.VISIBLE);
                    llBank.setVisibility(View.INVISIBLE);
                    break;

                case R.id.pay_bank:
                    payBank.setBackground(ContextCompat.getDrawable(PurchaseActivity.this,R.drawable.border));
                    payBank.setTextColor(Color.parseColor("#000000"));
                    payCard.setBackground(ContextCompat.getDrawable(PurchaseActivity.this,R.drawable.border_lignt));
                    payCard.setTextColor(Color.parseColor("#939393"));
                    llBank.setVisibility(View.VISIBLE);
                    llCard.setVisibility(View.INVISIBLE);
                    break;
            }

        }
    };


    /**
     * 텍스트 웟쳐
     */
    TextWatcher addTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (card1.length() == 4) {               // edit1 맥스값을 4이라고 가정했을때
                card2.requestFocus();             // 두번째로 포커스가 넘어간다
            }
            if (card2.length() == 4) {
                card3.requestFocus();
            }
            if (card3.length() == 4) {
                card4.requestFocus();
            }
            if (card4.length() == 4) {
                card5.requestFocus();
            }


        }



        @Override
        public void afterTextChanged(Editable s) {

        }
    };




    /**
     * null 체크 메소드
     */
    private void nullCheck(){

    }


    /**
     * ***************************제품 구매 버튼 ****************************
     */
    View.OnClickListener buyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            collectData();
            if (orderinfoInsert().equals("1")){
             Toast.makeText(PurchaseActivity.this, "ORDER INFO 입력 성공", Toast.LENGTH_SHORT).show();
            }
            orderDetailInsert(); // orderdetail 에 넣기
            cartDetailDelete();



            Intent intent = new Intent(PurchaseActivity.this, OrderFinished.class);
            intent.putExtra("buy_name", String.valueOf(purchaseUserName.getText()));
            intent.putExtra("buy_tel", String.valueOf(purchaseUserTel.getText()));
            intent.putExtra("buy_email", String.valueOf(purchaseUserEmail.getText()));
            intent.putExtra("buy_request", orderRequest);
            intent.putExtra("rcv_name", ordReceiver);
            intent.putExtra("rcv_tel", ordRcvPhone);
            intent.putExtra("rcv_address", ordRcvAddress);
            startActivity(intent);
            finish();



        }
    };


    /**
     * DB에 넣을 데이터 정리하기
     */
    private void collectData(){
        if (purchaseSpinner.getSelectedItemPosition() == 3){
            orderRequest = String.valueOf(et.getText());
        }else {
            orderRequest = String.valueOf(purchaseSpinner.getSelectedItem());
        }

        ordPay = totalPrice;

        ordCardNo = card1.getText().toString() + card2.getText().toString() + card3.getText().toString() + card4.getText().toString();
        Log.v(TAG,ordCardNo);
        ordCardPass = String.valueOf(card5.getText());

    }





    /**
     * orderinfo 에 INSERT DATA
     */
    private String orderinfoInsert(){
        String result = null;
//        String urlAddrInsert = "http://" + macIP + ":8080/JSP/orderinfo_insert.jsp?userEmail=" + userEmail + "&ordReceiver=" + ordReceiver + "&ordRcvAddress="+ ordRcvAddress + "&ordRcvPhone="+ ordRcvPhone + "&orderRequest="
//                + orderRequest + "&ordPay="+ ordPay + "&ordCardNo="+ ordCardNo + "&ordCardPass=" + ordCardPass;

        String urlAddrInsert = "http://" + macIP + ":8080/JSP/orderinfo_insert.jsp?userEmail=" + userEmail + "&ordReceiver=" + ordReceiver + "&ordRcvAddress='"+ ordRcvAddress + "'&ordRcvPhone="+ ordRcvPhone + "&orderRequest="
                + orderRequest + "&ordPay="+ ordPay + "&ordCardNo="+ ordCardNo + "&ordCardPass=" + ordCardPass;
        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.01.11
            //
            // Description:
            //  - 입력하고 리턴값을 받음
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            CartNetworkTask networkTask = new CartNetworkTask(PurchaseActivity.this, urlAddrInsert, "insert");
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

    /**
     *
     */
    private String checkOrderNo(){
        String result = null;

        String urlAddrCheck = "http://" + macIP + ":8080/JSP/ordNo_select.jsp?userEmail=" + userEmail;
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(PurchaseActivity.this, urlAddrCheck, "select");
            Object obj = cartNetworkTask.execute().get();
            result = String.valueOf(obj);


        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v(TAG, result);
        return result;


    }


    /**
     * orderdetail 에 구매한 제품 입력
     * @return
     */
    private void orderDetailInsert() {
        int count = 0;
        String urlAddr4 = "http://" + macIP + ":8080/JSP/orderdetail_insert.jsp?userEmail=" + userEmail + "&ordNo=" + checkOrderNo();
        for (int i = 0; i < getCartData.size(); i++) {
            String urlAddr5 = "&prdNo=" + getCartData.get(i).getPrdNo() + "&ordQty=" + getCartData.get(i).getCartQty();
            count += Integer.parseInt(connectInsertData(urlAddr4 + urlAddr5));
            Log.v(TAG, "count : " + count);
            Log.v(TAG, "urlAddr5 : " + urlAddr5);
        }
        if (count == getCartData.size()) {
            Toast.makeText(PurchaseActivity.this, "입력 성공하였습니다.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(PurchaseActivity.this, "입력 실패하였습니다.", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * cartdetail 제품 삭제
     * @return
     */
    private void cartDetailDelete() {
        int count = 0;
        String urlAddr4 = "http://" + macIP + ":8080/JSP/cartdetail_delete.jsp?userEmail=" + userEmail;
        for (int i = 0; i < getCartData.size(); i++) {
            String urlAddr5 = "&prdNo=" + getCartData.get(i).getPrdNo();
            count += Integer.parseInt(connectInsertData(urlAddr4 + urlAddr5));
            Log.v(TAG, "count : " + count);
            Log.v(TAG, "urlAddr5 : " + urlAddr5);
        }
        if (count == getCartData.size()) {
            Toast.makeText(PurchaseActivity.this, "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(PurchaseActivity.this, "삭제 실패하였습니다.", Toast.LENGTH_SHORT).show();

        }

    }

    private String connectInsertData(String urlAddrInsert){

        String result = null;

        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.01.11
            //
            // Description:
            //  - 입력하고 리턴값을 받음
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            CartNetworkTask networkTask = new CartNetworkTask(PurchaseActivity.this, urlAddrInsert, "insert");
            ///////////////////////////////////////////////////////////////////////////////////////

            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.24
            //
            // Description:
            //  - 입력 결과 값을 받기 위해 Object로 return후에 String으로 변환 하여 F사용
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











}// ----