package com.android.androidpj_main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidpj_main.Activity.PreferenceManager;
import com.android.androidpj_main.Activity.ProductViewActivity;
import com.android.androidpj_main.Bean.Cart;
import com.android.androidpj_main.NetworkTask.CartNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    final static String TAG = "CartAdapter";

    // 종한 추가 21.01.12 *************************************

    String macIP = ShareVar.macIP;

    Context mContext = null;
    int layout = 0;
    ArrayList<Cart> data = null;
    LayoutInflater inflater = null;
    String urlAddr;

    String userEmail;

    // 전체 체크용
    List<CheckBox> checkBoxList = new ArrayList<>();


    public CartAdapter(Context mContext, int layout, ArrayList<Cart> data){
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // 리스트뷰 메뉴가 처음 생성될 때 생명주기
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        CartHolder cartHolder = new CartHolder(v);
        return cartHolder;
    }

    public void checkBoxOperation(boolean what){
        for (CheckBox checkBox : checkBoxList ){
            checkBox.setChecked(what);
        }
    }



    // 추가 될때 생명주기
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        // 체크박스 리스트에 전부 추가하기
        checkBoxList.add(holder.cartCbEach);

        urlAddr = "http://" + ShareVar.macIP + ":8080/Images/";  // Images 파일
        urlAddr = urlAddr + data.get(position).getPrdFilename(); // 경로에 이미지 이름 추가

        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 90%;\"" +
                "</center></body>" +
                "</html>";
        holder.web_cart.loadData(htmlData,"text/html", "UTF-8");

        // 상품 브랜드
        holder.cart_brand.setText("[ "+data.get(position).getPrdBrand()+ " ]");
        // 상품 이름
        holder.cart_name.setText(data.get(position).getPrdName());
        // 상품 가격
        holder.cart_price.setText("0");
        // 상품 수량
        holder.cart_qty.setText(String.valueOf(data.get(position).getCartQty()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ProductViewActivity.class);
                intent.putExtra("prdName", data.get(position).getPrdName());

                v.getContext().startActivity(intent);

                Toast.makeText(v.getContext(), "상세보기 페이지 이동", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    // CartHolder
    public class CartHolder extends RecyclerView.ViewHolder {

        // item_cart.xml 선언
        WebView web_cart;
        protected TextView cart_brand;
        protected TextView cart_name;
        protected TextView cart_price;
        protected EditText cart_qty;

        // 수량 변경 버튼
        protected Button btnCartMinus;
        protected Button btnCartPlus;

        // 체크박스
        protected CheckBox cartCbEach;
        protected CheckBox cartCbAll;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            web_cart = itemView.findViewById(R.id.web_cart);
            cart_brand = itemView.findViewById(R.id.cart_pBrand);
            cart_name = itemView.findViewById(R.id.cart_pName);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_qty = itemView.findViewById(R.id.cart_quantity);
            Log.v(TAG, "cart_qty" + cart_qty);

            btnCartMinus = itemView.findViewById(R.id.cart_btn_minus);
            btnCartPlus = itemView.findViewById(R.id.cart_btn_plus);

            cartCbEach = itemView.findViewById(R.id.cart_cb_each);
            cartCbAll = itemView.findViewById(R.id.cart_cb_all);

            // WebView 세팅
            // Web Setting
            WebSettings webSettings = web_cart.getSettings();
            webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
            webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
            web_cart.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투명으로 전환

            // 버튼 클릭 리스너
            btnCartMinus.setOnClickListener(btnClickListener);
            btnCartPlus.setOnClickListener(btnClickListener);

            // 체크박스 체크 리스너
            cartCbEach.setOnCheckedChangeListener(checkedChangeListener);
           // cartCbAll.setOnCheckedChangeListener(checkedChangeListener);


        }

        // 수량 +, - 버튼
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmail = PreferenceManager.getString(v.getContext(), "email");
                // 수량
                int et_quan = Integer.parseInt(cart_qty.getText().toString());
                // 총 상품 금액
                int total = Integer.parseInt(String.valueOf(cart_price.getText()));
                Log.v(TAG, "total ;;;" + total);

                if (cartCbEach.isChecked()) {
                    switch (v.getId()) {
                        case R.id.cart_btn_plus: // 플러스 버튼
                            et_quan = et_quan + 1;
                            cart_qty.setText(String.valueOf(et_quan));
                            total = et_quan * data.get(getAdapterPosition()).getPrdPrice();
                            if (cartCbEach.isChecked()) {
                                cart_price.setText(String.valueOf(total));
                            }
                            Log.v(TAG, "증가값::::" + et_quan + "Total값:::: " + total);
                            cartUpdateData();
                            break;

                        case R.id.cart_btn_minus:  // 마이너스 버튼
                            if (et_quan == 1) {
                                Toast.makeText(mContext, "최소 주문수랑은 1개 입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                et_quan = et_quan - 1;
                            }
                            cart_qty.setText(String.valueOf(et_quan));
                            total = et_quan * data.get(getAdapterPosition()).getPrdPrice();
                            if (cartCbEach.isChecked()) {
                                cart_price.setText(String.valueOf(total));
                            }
                            Log.v(TAG, "감소값::::" + et_quan);
                            cartUpdateData();
                            break;
                    }
                }
            }
        };

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() { // CompoundButton = 체크박스
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // 어느 버튼이냐. 체크가 되어있냐 안되어있냐
                String str = ""; // 버튼 누를 때마다 toast는 초기화

                ArrayList<Integer> btns = new ArrayList<Integer>();
                btns.add(R.id.cart_cb_each);


                for (int i = 0; i < btns.size(); i ++){
                    cartCbEach = itemView.findViewById(btns.get(i));
                    if (cartCbEach.isChecked() == true){

                        str += cartCbEach.getText().toString();
                        Log.v(TAG, String.valueOf(getAdapterPosition()));
                        // 상품 가격
                        Log.v(TAG, String.valueOf(data.get(getAdapterPosition()).getPrdPrice()));
                        cart_price.setText(String.valueOf(Integer.parseInt(String.valueOf(cart_qty.getText()))*data.get(getAdapterPosition()).getPrdPrice()));
                    }else {
                        cart_price.setText("0");

                    }

                }

                if (str == ""){

                }else {
                    Log.v(TAG, "checkbox str ;;;" + str);

                }


            }
        };

        // 장바구니에 상품 Update
        private String cartUpdateData(){
            String result = null;

            String urlAddrUpdate = "http://" + macIP + ":8080/JSP/cart_update_inCart.jsp?userEmail=" + userEmail + "&prdName=" + data.get(getAdapterPosition()).getPrdName() + "&cartQty=";
            urlAddrUpdate = urlAddrUpdate + cart_qty.getText().toString();
            Log.v(TAG, "cartUpdateQty =" + cart_qty.getText().toString());
            try {
                ///////////////////////////////////////////////////////////////////////////////////////
                // Date : 2020.01.11
                //
                // Description:
                //  - 수정하고 리턴값을 받음
                //
                ///////////////////////////////////////////////////////////////////////////////////////
                CartNetworkTask networkTask = new CartNetworkTask(mContext, urlAddrUpdate, "update");
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

        public void cart_select_all(){

        }



    }



} // ---
