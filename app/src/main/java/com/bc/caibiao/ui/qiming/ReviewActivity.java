package com.bc.caibiao.ui.qiming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.TopBarLayout;

public class ReviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initView();
    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {


    }
}
