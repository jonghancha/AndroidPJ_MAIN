package com.android.androidpj_main.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.Bean.Product;
import com.android.androidpj_main.NetworkTask.CUDNetworkTask;
import com.android.androidpj_main.NetworkTask.LikeCheckNetworkTask;
import com.android.androidpj_main.NetworkTask.ProductViewNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.util.ArrayList;

public class ProductViewActivity extends AppCompatActivity {

    ///////////////////////////////////////////////////////////////////////////
    //
    //  21.01.09 세미 생성
    //  제품 상세보기 페이지
    //
    ///////////////////////////////////////////////////////////////////////////

    final static String TAG = "ProductView";
    TextView tv_prdName, tv_prdBrand, tv_prdPrice;
    WebView wv_prdimg, wv_prdDFile, wv_prdNFile;
    String prdNo, prdName, prdName2;
    int prdPrice = 0;
    int result = 0;
    Button btn_buy;
    BottomSheet bottomSheet;
    String urlAddr = null;
    String urlAddr1 = null;
    String email = null;
    String prdFileName, prdDFileName, prdNFileName;
    ImageButton ib_like;
    ArrayList<Product> data = null;
    ImageButton btn_prdview, btn_prdreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        setTitle("상세 보기");
        email = PreferenceManager.getString(ProductViewActivity.this, "email");

        // 연결하기
        tv_prdName = findViewById(R.id.prdName);
        btn_buy = findViewById(R.id.btn_buy);
        ib_like = findViewById(R.id.btn_like);
        wv_prdimg = findViewById(R.id.wv_prdimg);
        tv_prdBrand = findViewById(R.id.prdBrand);
        tv_prdPrice = findViewById(R.id.prdPrice);
        wv_prdDFile = findViewById(R.id.wv_prdDFile);
        wv_prdNFile = findViewById(R.id.wv_prdNFile);
        btn_prdview = findViewById(R.id.btn_prdview);
        btn_prdreview = findViewById(R.id.btn_prdreview);

        // intent 가져오기
        Intent intent = getIntent();
        prdNo = Integer.toString(intent.getIntExtra("prdNo", 0));
        prdName = intent.getStringExtra("prdName");

        // 상품 이름에 대한 상품 정보 가져오기
        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/productViewSelect.jsp?prdNo=" + prdNo;
        productselect();

        // 정보 가져오기
        prdName2 = data.get(0).getPrdName();
        prdPrice = data.get(0).getPrdPrice();
        prdFileName = data.get(0).getPrdFilename();
        prdDFileName = data.get(0).getPrdDFilename();
        prdNFileName = data.get(0).getPrdNFilename();

        // 정보 띄워주기
        tv_prdName.setText(prdName2);
        tv_prdBrand.setText(data.get(0).getPrdBrand());
        tv_prdPrice.setText(String.valueOf(prdPrice));

        // like 테이블에 있는지 체크
        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likeCheck.jsp?user_userEmail=" + email + "&product_prdNo=" + prdNo;
        result = likeCheck();

        // like체크 후 이미지 변경
        if (result == 1){
            ib_like.setImageResource(R.drawable.ic_like);
        }else {
            ib_like.setImageResource(R.drawable.ic_hate);
        }

        // like 버튼 클릭
        ib_like.setOnClickListener(mClickListener);

        // 버튼 클릭시
        btn_buy.setOnClickListener(mClickListener);

        // Web Setting
        WebSettings webSettings = wv_prdimg.getSettings();
        webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
        webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
        webSettings.setDisplayZoomControls(false); // 돋보기 없애기
        wv_prdimg.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투명으로 전환

        // 상품사진
        urlAddr = "http://" + ShareVar.macIP + ":8080/Images/";  // Images 파일
        urlAddr = urlAddr + prdFileName; // 경로에 이미지 이름 추가
        Log.v(TAG, "이미지 ::::::" + urlAddr);
        String htmlData = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 90%;\">" +
                "</center></body>" +
                "</html>";
        wv_prdimg.loadData(htmlData,"text/html", "UTF-8");

        // 상품성분
        urlAddr = "http://" + ShareVar.macIP + ":8080/Images/";  // Images 파일
        urlAddr = urlAddr + prdDFileName; // 경로에 이미지 이름 추가
        Log.v(TAG, "이미지 ::::::" + urlAddr);
        String htmlData2 = "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body><center>" +
                "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 100%;\">" +
                "</center></body>" +
                "</html>";
        wv_prdDFile.loadData(htmlData2,"text/html", "UTF-8");

    // 상품정보
    urlAddr = "http://" + ShareVar.macIP + ":8080/Images/";  // Images 파일
    urlAddr = urlAddr + prdNFileName; // 경로에 이미지 이름 추가
        Log.v(TAG, "이미지 ::::::" + urlAddr);
    String htmlData3 = "<html>" +
            "<head>" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
            "</head>" +
            "<body><center>" +
            "<img src = \"" + urlAddr + "\"style=\"width: auto; height: 100%;\">" +
            "</center></body>" +
            "</html>";
        wv_prdNFile.loadData(htmlData3,"text/html", "UTF-8");


        btn_prdview.setOnClickListener(mClickListener);
    }




    // 구매하기 버튼 클릭시
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // BottomSheet에 값 넘겨주기
//            Bundle bundle = new Bundle();
//            bundle.putString("prdNo", prdNo);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            BottomSheet bottomSheet = new BottomSheet();
//            bottomSheet.setArguments(bundle);
            switch (v.getId()){

                case R.id.btn_buy:
                        Intent intent = new Intent(ProductViewActivity.this, BottomSheet.class);
                        bottomSheet = new BottomSheet();
                        bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
                        break;

                case R.id.btn_like:

                    if(result == 1){    // 이미 찜 상태 - like테이블에서 삭제, 버튼 변경
                        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likeDel.jsp?prdNo=" + prdNo + "&email=" + email;
                        connectDeleteData();
                        Toast.makeText(ProductViewActivity.this, "찜목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        ib_like.setImageResource(R.drawable.ic_hate);
                        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likeCheck.jsp?user_userEmail=" + email + "&product_prdNo=" + prdNo;
                        result = likeCheck();

                    }else{              // 찜으로 변경 - like테이블에 insert, 버튼 변경
                        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likebtnChange.jsp?prdNo=" + prdNo + "&email=" + email;
                        connectDeleteData();
                        Toast.makeText(ProductViewActivity.this, "찜목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        ib_like.setImageResource(R.drawable.ic_like);
                        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likeCheck.jsp?user_userEmail=" + email + "&product_prdNo=" + prdNo;
                        result = likeCheck();

                    }

                    break;

                case R.id.btn_prdview:
                    PrdDialogFragment dialogFragment = new PrdDialogFragment();
                    dialogFragment.show(getSupportFragmentManager(), "MyFragment");

            }

        }
    };

    // 제품 정보 가져오기
    public void productselect(){
        try{
            ProductViewNetworkTask productViewNetworkTask = new ProductViewNetworkTask(ProductViewActivity.this, urlAddr);
            Object obj = productViewNetworkTask.execute().get();
            data = (ArrayList<Product>) obj;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 찜목록 체크
    public int likeCheck(){

        int result = 0;

        try {

        LikeCheckNetworkTask likeCheckNetworkTask = new LikeCheckNetworkTask(ProductViewActivity.this, urlAddr);

        Object obj = likeCheckNetworkTask.execute().get();
        result = (int) obj;
        } catch (Exception e){
            e.printStackTrace();
        }
            return result;
    }

    // 찜 버튼 클릭시, 추가, 제거
    private void connectDeleteData(){
        try{
            CUDNetworkTask deleteworkTask = new CUDNetworkTask(ProductViewActivity.this, urlAddr);
            deleteworkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //finish();
    }


}//----------------