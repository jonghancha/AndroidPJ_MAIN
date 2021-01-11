package com.android.androidpj_main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.NetworkTask.LikeCheckNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

public class ProductViewActivity extends AppCompatActivity {

    ///////////////////////////////////////////////////////////////////////////
    //
    //  21.01.09 세미 생성
    //  제품 상세보기 페이지
    //
    ///////////////////////////////////////////////////////////////////////////

    final static String TAG = "ProductView";
    TextView prdName;
    String prdNo;
    int prdPrice;
    Button btn_buy;
    BottomSheet bottomSheet;
    String urlAddr = null;
    ImageButton ib_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        String email = PreferenceManager.getString(ProductViewActivity.this, "email");

        // 연결하기
        prdName = findViewById(R.id.prdName);
        btn_buy = findViewById(R.id.btn_buy);
        ib_like = findViewById(R.id.btn_like);

        // intent 가져오기
        Intent intent = getIntent();
        prdNo = Integer.toString(intent.getIntExtra("prdNo", 0));
        prdName.setText(intent.getStringExtra("prdName"));
        prdPrice = intent.getIntExtra("prdPrice", 0);

        // like 테이블에 있는지 체크
        urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/likeCheck.jsp?user_userEmail=" + email + "&product_prdNo=" + prdNo;
        int result = likeCheck();

        if (result == 1){
            Toast.makeText(ProductViewActivity.this, "찜한상품", Toast.LENGTH_SHORT).show();
            ib_like.setImageResource(R.drawable.ic_like);
        }else {
            Toast.makeText(ProductViewActivity.this, "안찜한상품", Toast.LENGTH_SHORT).show();
            ib_like.setImageResource(R.drawable.ic_hate);
        }

        // 버튼 클릭시
        btn_buy.setOnClickListener(mClickListener);

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

            Intent intent = new Intent(ProductViewActivity.this, BottomSheet.class);
            intent.putExtra("prdNo", prdNo);
            intent.putExtra("prdPrice",prdPrice);
            bottomSheet = new BottomSheet();
            bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
;
        }
    };

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


}//----------------