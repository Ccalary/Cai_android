package com.bc.caibiao.ui.shangbiao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;

public class ShangbiaoSearchActivity extends BaseActivity  {

    private android.widget.LinearLayout llWord;
    private android.widget.LinearLayout llPic;
    private android.widget.ImageView ivMall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangbiao_search);
        this.ivMall = (ImageView) findViewById(R.id.ivMall);
        this.llPic = (LinearLayout) findViewById(R.id.llPic);
        this.llWord = (LinearLayout) findViewById(R.id.llWord);

        initView();
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ivMall://购物车
                Intent classifyIntent=new Intent(ShangbiaoSearchActivity.this,ShangbiaoClassificationActivity.class);
                startActivity(classifyIntent);
                break;
            case R.id.llPic://图片搜索
                Intent picIntent=new Intent(ShangbiaoSearchActivity.this,PicSearchActivity.class);
                startActivity(picIntent);
                break;
            case R.id.llWord://文字搜索
                Intent wordIntent=new Intent(ShangbiaoSearchActivity.this,WordSearchActivity.class);
                startActivity(wordIntent);
                break;
        }
    }

    private void initView() {
        ivMall.setOnClickListener(this);
        llPic.setOnClickListener(this);
        llWord.setOnClickListener(this);
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
