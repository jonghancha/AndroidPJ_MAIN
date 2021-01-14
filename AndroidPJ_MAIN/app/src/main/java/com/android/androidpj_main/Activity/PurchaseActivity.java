package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class PurchaseActivity extends Activity {

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


    // 총 결제금액 넣어주기
    TextView purchaseTotalPrice;



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


        // 총 결제금액 넣어주기
        purchaseTotalPrice = findViewById(R.id.purchase_totalprice);




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
            intent.putExtra("purName", purchaseUserName.getText());
            intent.putExtra("purTel", purchaseUserTel.getText());
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
                String name = data.getExtras().getString("name");
                Toast.makeText(getApplicationContext(), "응답값 : " + name, Toast.LENGTH_LONG).show();
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
                EditText et = new EditText(getApplicationContext());
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

}// ----