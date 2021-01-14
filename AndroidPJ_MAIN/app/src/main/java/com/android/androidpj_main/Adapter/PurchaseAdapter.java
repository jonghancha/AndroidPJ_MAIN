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

import com.android.androidpj_main.Activity.OnChangeCheckedPrice;
import com.android.androidpj_main.Activity.PreferenceManager;
import com.android.androidpj_main.Activity.ProductViewActivity;
import com.android.androidpj_main.Bean.Cart;
import com.android.androidpj_main.NetworkTask.CartNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.CartHolder> {

    final static String TAG = "CartAdapter";

    // 종한 추가 21.01.14 *************************************

    String macIP = ShareVar.macIP;

    Context mContext = null;
    int layout = 0;
    ArrayList<Cart> data = null;

    LayoutInflater inflater = null;
    String urlAddr;

    String userEmail;

    CartHolder cartHolder;

    // 전체 체크용
//    List<CheckBox> checkBoxList = new ArrayList<>();
    List<EditText> editTextList = new ArrayList<>();

    ArrayList<Cart> sendCartData;

    ArrayList<Integer> selectedPosition;

    public PurchaseAdapter(Context mContext, int layout, ArrayList<Cart> data ){
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
        v = LayoutInflater.from(mContext).inflate(R.layout.item_purchase, parent, false);
        cartHolder = new CartHolder(v);
        return cartHolder;
    }




    /**
     *
     * 결제 창으로 보낼 데이터 전송
     */
    public ArrayList<Cart> sendToOrder(){
        return sendCartData;
    }



    /**
     * 카트 삭제 메소드
     */
    // 장바구니 선택 제품 삭제
    public void connectDeleteData() {

        if (sendCartData.size() == 0) {
            Toast.makeText(mContext, "삭제할 제품을 선택해주세요", Toast.LENGTH_SHORT).show();
        } else {
            String urlAddrDelete;

            for (int i = 0; i < sendCartData.size(); i++) {
                urlAddrDelete = "http://" + macIP + ":8080/JSP/cart_delete_inCart.jsp?userEmail=" + userEmail + "&goods_prdNo=";
                urlAddrDelete = urlAddrDelete + sendCartData.get(i).getPrdNo();
                try {
                    CartNetworkTask networkTask = new CartNetworkTask(mContext, urlAddrDelete, "delete");
                    networkTask.execute().get();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }




    // 추가 될때 생명주기
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        // 텍스트뷰 리스트에 전부 추가하기
        editTextList.add(holder.purchase_qty);

        urlAddr = "http://" + ShareVar.macIP + ":8080/Images/";  // Images 파일
        urlAddr = urlAddr + data.get(position).getPrdFilename(); // 경로에 이미지 이름 추가

        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 90%;\">" +
                "</center></body>" +
                "</html>";
        holder.web_cart.loadData(htmlData,"text/html", "UTF-8");

        // 상품 브랜드
        holder.purchase_brand.setText("[ "+data.get(position).getPrdBrand()+ " ]");
        // 상품 이름
        holder.purchase_name.setText(data.get(position).getPrdName());
        // 상품 가격
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(data.get(position).getPrdPrice());
        holder.purchase_price.setText(formattedStringPrice);
        // 상품 수량
        holder.purchase_qty.setText(String.valueOf(data.get(position).getCartQty()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ProductViewActivity.class);
                intent.putExtra("prdName", data.get(position).getPrdNo());

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
        protected TextView purchase_brand;
        protected TextView purchase_name;
        protected TextView purchase_price;
        protected EditText purchase_qty;





//        // 체크 된 제품들 데이터만 들어감
//        ArrayList<Cart> sendCartData;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            web_cart = itemView.findViewById(R.id.web_purchase);
            purchase_brand = itemView.findViewById(R.id.purchase_brand);
            purchase_name = itemView.findViewById(R.id.purchase_pName);
            purchase_price = itemView.findViewById(R.id.purchase_price);
            purchase_qty = itemView.findViewById(R.id.purchase_quantity);
            Log.v(TAG, "purchase_qty" + purchase_qty);


            // WebView 세팅
            // Web Setting
            WebSettings webSettings = web_cart.getSettings();
            webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
            webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
            web_cart.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투명으로 전환



            sendCartData = new ArrayList<Cart>();
            userEmail = PreferenceManager.getString(itemView.getContext(), "email");

            selectedPosition = new ArrayList<Integer>();




        }

        // 수량 +, - 버튼
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmail = PreferenceManager.getString(v.getContext(), "email");
                // 수량
                int et_quan = Integer.parseInt(purchase_qty.getText().toString());
                // 총 상품 금액
                int total = 0;
                Log.v(TAG, "total ;;;" + total);


            }
        };



        // 장바구니에 상품 Update
        private String cartUpdateData() {
            String result = null;

            String urlAddrUpdate = "http://" + macIP + ":8080/JSP/cart_update_inCart.jsp?userEmail=" + userEmail + "&prdNo=" + data.get(getAdapterPosition()).getPrdNo() + "&cartQty=";
            urlAddrUpdate = urlAddrUpdate + purchase_qty.getText().toString();
            Log.v(TAG, "cartUpdateQty =" + purchase_qty.getText().toString());
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

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }









    }








} // ---
