package com.bc.caibiao.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;

import com.bc.caibiao.BaseFragmentActivity;
import com.bc.caibiao.R;
import com.bc.caibiao.adapter.GuideAdapter;
import com.bc.caibiao.ui.guide.GuideFragment1;
import com.bc.caibiao.ui.guide.GuideFragment2;
import com.bc.caibiao.ui.guide.GuideFragment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengyanfang on 2017/4/24.
 */

public class GuideAct  extends BaseFragmentActivity implements ViewPager.OnPageChangeListener {

    ViewPager mViewPager;
    GuideAdapter mAdapter;

    GuideFragment1 guideFragment1;
    GuideFragment2 guideFragment2;
    GuideFragment3 guideFragment3;

    ImageView tipImg1, tipImg2;

    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle arg0) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(arg0);
        setContentView(R.layout.activity_guide_layout);

        initUI();
    }

    @Override
    protected void initUI() {
        super.initUI();
        mViewPager = (ViewPager) findViewById(R.id.guide_viewPager);


        guideFragment1 = new GuideFragment1();
        guideFragment2 = new GuideFragment2();
        guideFragment3 = new GuideFragment3();

        fragments.add(guideFragment1);
        fragments.add(guideFragment2);
        fragments.add(guideFragment3);

        mAdapter = new GuideAdapter(getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);


        mViewPager.setOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("onPageScrolled:", "position:" + position + "  positionOffset:"
                + positionOffset + "  positionOffsetPixels:" + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(final int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
