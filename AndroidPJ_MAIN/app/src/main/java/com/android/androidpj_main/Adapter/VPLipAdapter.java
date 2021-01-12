package com.android.androidpj_main.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

// 지은 추가 21.01.07 ***************************
public class VPLipAdapter extends FragmentPagerAdapter {


    private final List<Fragment> SlstFrmt = new ArrayList<>();
    private final List<String> SlstTitles = new ArrayList<>();

    public VPLipAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return SlstFrmt.get(position);
    }

    @Override
    public int getCount() {
        return SlstTitles.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return SlstTitles.get(position);
    }

    public void AddFrmt(Fragment fragment, String titles){

        SlstFrmt.add(fragment);
        SlstTitles.add(titles);
    }
}
