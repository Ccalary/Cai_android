package com.bc.caibiao.ui.qiming;

import android.os.Bundle;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chengyanfang on 2017/4/19.
 */

public class MasterDescAct extends BaseActivity{

    @Bind(R.id.desc)
    TextView mDescTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marster_detail_desc);
        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        mDescTv.setText(getIntent().getStringExtra("desc"));
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
