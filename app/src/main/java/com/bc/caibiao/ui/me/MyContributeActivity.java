package com.bc.caibiao.ui.me;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.TopBarLayout;
import com.bc.caibiao.widget.SimpleViewPagerIndicator.SimpleViewPagerIndicator;

import butterknife.Bind;

public class MyContributeActivity extends BaseActivity {

    SimpleViewPagerIndicator mIndicator;
    ViewPager mViewPager;
    private String[] mTitles = new String[]{"全部", "进行中","已结束"};

    TopBarLayout mTopBarLayout;

    FragmentPagerAdapter mAdapter;

    MyPostTaskFragment mAllTAskFragment;
    MyPostTaskFragment mGoingTaskFragment;
    MyPostTaskFragment mFinishTaskFragment;

    BaseFragment[] mFragments = new BaseFragment[mTitles.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        initView();
    }

    private void initView() {
        mIndicator = (SimpleViewPagerIndicator)findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mAllTAskFragment = new MyPostTaskFragment(0);
        mGoingTaskFragment = new MyPostTaskFragment(1);
        mFinishTaskFragment = new MyPostTaskFragment(2);

        mFragments[0] = mAllTAskFragment;
        mFragments[1] = mGoingTaskFragment;
        mFragments[2] = mFinishTaskFragment;

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

        mTopBarLayout = (TopBarLayout)findViewById(R.id.rlv_title);
        mTopBarLayout.setTitle("我的投稿");

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

                if(position == 0){
                    mAllTAskFragment.refreshData();
                }else if(position == 1){
                    mGoingTaskFragment.refreshData();
                }else{
                    mFinishTaskFragment.refreshData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAllTAskFragment.refreshData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
