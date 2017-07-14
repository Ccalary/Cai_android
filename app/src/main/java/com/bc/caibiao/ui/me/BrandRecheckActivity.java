package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.TaskAdapter;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.model.RewardInfo;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.SearchServiceAct;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created  on 2017/4/19.
 * 复查列表
 */

public class BrandRecheckActivity extends BaseActivity {
    private Member mMember;
    /**
     * UI元素
     * */
    @Bind(R.id.ll_recheck)
    LinearLayout mRecheck;

    @Bind(R.id.ed_recheck_markname)
    EditText mRecheckMarkName;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.tablayout)
    TabLayout mTab;

    private List<RewardInfo> mShowItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_brand_recheck);
        initView();
    }

    private void initView() {
        mTab = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mRecheck = (LinearLayout) findViewById(R.id.ll_recheck);
        mRecheck.setOnClickListener(this);
        mRecheckMarkName = (EditText) findViewById(R.id.ed_recheck_markname);

        mShowItems.add(new RewardInfo(new AllRecheckFragment(),"全部"));
        mShowItems.add(new RewardInfo(new RecheckDoingFragment(),"进行中"));
        mShowItems.add(new RewardInfo(new RecheckDoneFragment(),"已结束"));

        //初始化veiwpager
        mViewPager.setAdapter(new TaskAdapter(getSupportFragmentManager(), mShowItems));

        //绑定tab和Viewpager
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_recheck:
                Intent intent = new Intent(this, SearchServiceAct.class);
                intent.putExtra("ShangBiaoName", mRecheckMarkName.getText().toString().trim());
                startActivity(intent);
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
