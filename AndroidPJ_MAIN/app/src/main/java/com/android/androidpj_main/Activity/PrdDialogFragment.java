package com.android.androidpj_main.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.androidpj_main.R;

public class PrdDialogFragment extends DialogFragment {

    // 광고 다이얼로그
    // 광고 끄기 버튼
    ImageView ivAdClose;

    // 다시보지않기 체크박스
    CheckBox cbAdStop;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.dialog_frmt_product, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // fragment에서 getView()를 통해 id 가져오는 법
        ivAdClose = getView().findViewById(R.id.iv_ad_close);

        // X 버튼 클릭 시 dialog 사라짐.
        ivAdClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
