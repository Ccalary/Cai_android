package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.TopBarLayout;

/**
 * 投稿
 */

public class ContributorActivity extends BaseActivity {
    private com.bc.caibiao.view.TopBarLayout topBar;
    private android.widget.EditText etBrandName;
    private android.widget.TextView tvNotQuery;
    private android.widget.TextView tvQuery;
    private android.widget.EditText etInterpretation;
    private android.widget.TextView tvWordNumber;
    private android.widget.LinearLayout llContent;
    private android.widget.TextView tvQueryCenter;
    private android.widget.TextView tvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor);
        initView();
    }

    private void initView() {
        this.tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        this.tvSubmit.setOnClickListener(this);
        this.tvQueryCenter = (TextView) findViewById(R.id.tvQueryCenter);
        this.tvQueryCenter.setOnClickListener(this);
        this.llContent = (LinearLayout) findViewById(R.id.llContent);
        this.tvWordNumber = (TextView) findViewById(R.id.tvWordNumber);
        this.etInterpretation = (EditText) findViewById(R.id.etInterpretation);
        this.tvQuery = (TextView) findViewById(R.id.tvQuery);
        this.tvQuery.setOnClickListener(this);
        this.tvNotQuery = (TextView) findViewById(R.id.tvNotQuery);
        this.tvNotQuery.setOnClickListener(this);
        this.etBrandName = (EditText) findViewById(R.id.etBrandName);
        this.topBar = (TopBarLayout) findViewById(R.id.topBar);

    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                finish();//回到详情页面

//                startActivity(new Intent(mContext, TaskDetailActivity.class));
                break;
            case R.id.tvQueryCenter:
                break;
            case R.id.tvQuery:
                break;
            case R.id.tvNotQuery:
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
