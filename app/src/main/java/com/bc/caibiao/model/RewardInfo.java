package com.bc.caibiao.model;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/3/21.
 * 
 */

public class RewardInfo {
    public Fragment fragment;
    public String title;


    public RewardInfo(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }
}
