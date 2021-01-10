package com.android.androidpj_main.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.androidpj_main.Adapter.ViewPageAdapter;
import com.android.androidpj_main.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

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
    Button btn_buy;
    BottomSheet bottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        // 연결하기
        prdName = findViewById(R.id.prdName);
        btn_buy = findViewById(R.id.btn_buy);

        // intent 가져오기
        Intent intent = getIntent();
        prdNo = Integer.toString(intent.getIntExtra("prdNo", 0));
        prdName.setText(intent.getStringExtra("prdName"));
        Log.v(TAG, "prddname ::::: "+ intent.getStringExtra("prdName") + "  prdno ::::: " + prdNo);

        // 버튼 클릭시
        btn_buy.setOnClickListener(mClickListener);

    }
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

            bottomSheet = new BottomSheet();
            bottomSheet.show(getSupportFragmentManager(),bottomSheet.getTag());
;
        }
    };

}//----------------