package com.android.androidpj_main.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.androidpj_main.UserSign.Frmt_Signup_Basic;
import com.android.androidpj_main.UserSign.Frmt_Signup_Gender;
import com.android.androidpj_main.UserSign.Frmt_Signup_Tone;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Frmt_Signup_Basic frmt_signup_basic = new Frmt_Signup_Basic();
                return frmt_signup_basic;
            case 1:
                Frmt_Signup_Gender frmt_signup_gender = new Frmt_Signup_Gender();
                return frmt_signup_gender;
            case 2:
                Frmt_Signup_Tone frmt_signup_tone = new Frmt_Signup_Tone();
                return frmt_signup_tone;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
