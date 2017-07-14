package com.bc.caibiao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bc.caibiao.model.RewardInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public class ShangbiaoClassifyAdapter extends FragmentStatePagerAdapter {
    private List<RewardInfo> mShowItems = new ArrayList<>();

    public ShangbiaoClassifyAdapter(FragmentManager fm, List<RewardInfo> mShowItems) {
        super(fm);
        this.mShowItems = mShowItems;
    }


    @Override
    public Fragment getItem(int position) {
        return mShowItems.get(position).fragment;
    }

    @Override
    public int getCount() {
        return mShowItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mShowItems.get(position).title;
    }
}
