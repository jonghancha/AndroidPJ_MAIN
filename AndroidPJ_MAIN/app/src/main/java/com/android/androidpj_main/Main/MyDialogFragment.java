package com.android.androidpj_main.Main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.androidpj_main.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDialogFragment extends DialogFragment {

    // 광고 다이얼로그
    // 광고 끄기 버튼
    ImageView ivAdClose;

    // 다시보지않기 체크박스
    CheckBox cbAdStop;

    // Debug tag, for logging
    static final String TAG = "MyDialogFragment";

    // SharedPreferences 정의
    private SharedPreferences SPreferences;

    // SharedPreferences 접근 이름, 저장 데이터 초기화
    private final String NameSPreferences = "Day";
    private String strSDFormatDay = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // '오늘 그만 보기' 기능을 위한 날짜 획득
        long CurrentTime = System.currentTimeMillis(); // 현재 시간을 msec 단위로 얻음
        Date TodayDate = new Date(CurrentTime); // 현재 시간 Date 변수에 저장
        SimpleDateFormat SDFormat = new SimpleDateFormat("dd");
        strSDFormatDay = SDFormat.format(TodayDate); // 'dd' 형태로 포맷 변경

        // SharedPreferences 획득
        SPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        return inflater.inflate(R.layout.dialog_frmt_ad, container, false);

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

        //
        cbAdStop = getView().findViewById(R.id.cb_ad_stop);

        //
        cbAdStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor SPreferencesEditor = SPreferences.edit();
                SPreferencesEditor.putString(NameSPreferences, strSDFormatDay); // 오늘 '일(day)' 저장
                SPreferencesEditor.commit(); // important to save the preference
                Log.v(TAG, "Close for a day");

                dismiss();
            }
        });
    }
}