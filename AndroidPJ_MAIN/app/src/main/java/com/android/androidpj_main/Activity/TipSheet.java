package com.android.androidpj_main.Activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.androidpj_main.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


// 지은 추가 21.01.13 *************************************
public class TipSheet extends BottomSheetDialogFragment {

    View v;
    TextView tip01, tip02, tip03, tip04, tip05, tip06, tip07;
    String option;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.activity_tip_sheet, container, false);

        tip01 = v.findViewById(R.id.tip_01);
        tip02 = v.findViewById(R.id.tip_02);
        tip03 = v.findViewById(R.id.tip_03);
        tip04 = v.findViewById(R.id.tip_04);
        tip05 = v.findViewById(R.id.tip_05);
        tip06 = v.findViewById(R.id.tip_06);
        tip07 = v.findViewById(R.id.tip_07);

        tip01.setOnClickListener(optionOnClickListener);
        tip02.setOnClickListener(optionOnClickListener);
        tip03.setOnClickListener(optionOnClickListener);
        tip04.setOnClickListener(optionOnClickListener);
        tip05.setOnClickListener(optionOnClickListener);
        tip06.setOnClickListener(optionOnClickListener);
        tip07.setOnClickListener(optionOnClickListener);

        return v;
    }



    View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tip_01:
                    option = "피부 복합성 피부에 대한 꿀팁";
                    ((HoneyTipActivity)getActivity()).changeText(option);
                    dismiss();
                    break;
                case R.id.tip_02:
                    option = "피부 건성 피부에 대한 꿀팁";
                    ((HoneyTipActivity)getActivity()).changeText(option);
                    dismiss();
                    break;
                case R.id.tip_03:
                    option = "피부 지성 피부에 대한 꿀팁";
                    ((HoneyTipActivity)getActivity()).changeText(option);
                    dismiss();
                    break;
                case R.id.tip_04:
                    option = "퍼스널 컬러 봄 웜톤에 대한 꿀팁";
                    ((HoneyTipActivity)getActivity()).changeText(option);
                    dismiss();
                    break;
                case R.id.tip_05:
                    option = "퍼스널 컬러 여름 쿨톤에 대한 꿀팁";
                    ((HoneyTipActivity)getActivity()).changeText(option);
                    dismiss();
                    break;
                case R.id.tip_06:
                    option = "퍼스널 컬러 가을 웜톤에 대한 꿀팁";
                    ((HoneyTipActivity)getActivity()).changeText(option);
                    dismiss();
                    break;
                case R.id.tip_07:
                    option = "퍼스널 컬러 겨울 쿨톤에 대한 꿀팁";
                    ((HoneyTipActivity)getActivity()).changeText(option);
                    dismiss();
                    break;
            }
        }
    };





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.btn_tip_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
