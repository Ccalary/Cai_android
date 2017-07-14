package com.bc.caibiao.ui.shangbiao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.widget.SimpleViewPagerIndicator.SimpleViewPagerIndicator;

public class ShangbiaoClassificationActivity extends BaseActivity {

    SimpleViewPagerIndicator mIndicator;
    ViewPager mViewPager;
    private int currentP = 0;
    private String[] mTitles = new String[]{"平台服务", "商标转让"};

    FragmentPagerAdapter mAdapter;
    ShangbiaoPlatformFragment mLeftShangbiaoPlatformFragment;
    ShangbiaoPlatformFragment mRightShangbiaoPlatformFragment;
    BaseFragment[] mFragments = new BaseFragment[mTitles.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangbiao_classification);
        initView();
    }

    private void initView() {
        mIndicator = (SimpleViewPagerIndicator)findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mLeftShangbiaoPlatformFragment = new ShangbiaoPlatformFragment(0);
        mRightShangbiaoPlatformFragment = new ShangbiaoPlatformFragment(1);
        mFragments[0] = mLeftShangbiaoPlatformFragment;
        mFragments[1] = mRightShangbiaoPlatformFragment;

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);

        //配置指示器
        findViewById(R.id.tab_bar_layout).setVisibility(View.VISIBLE);
        mIndicator.setTitles(mTitles);
        mIndicator.setaChangeIndicatorCallback(new SimpleViewPagerIndicator.ChangeIndicatorCallback() {
            @Override
            public void changeWithCurrentIndex(int index) {
                mViewPager.setCurrentItem(index);
            }
        });

        //配置ViewPager
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.setChildTextSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void OnClick(View v) {

    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
