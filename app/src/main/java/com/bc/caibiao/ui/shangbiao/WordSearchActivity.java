package com.bc.caibiao.ui.shangbiao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.utils.MarkModuleUtil;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.ClearEditText;

/**
 *
 * 文字搜索页面
 *
 * @author chengyanfang
 *
 * */
public class WordSearchActivity extends BaseActivity  {

    ClearEditText mClearEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangbiao_word_search);
        initView();
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                onClickSearch();
                break;
        }
    }

    private void initView() {
        findViewById(R.id.tvSubmit).setOnClickListener(this);
        mClearEditText = (ClearEditText)findViewById(R.id.etWord);
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    /**
     * 点击搜索
     * */
    private void onClickSearch(){
        if(TextUtils.isEmpty(mClearEditText.getText().toString())){
            ToastUtils.showShort(this,"请输入商品名称");
            return;
        }

        //是否登录
        if(!MarkModuleUtil.isLogin()){
            Intent aIntent = new Intent(this, LoginActivity.class);
            aIntent.putExtra("isNeedBack",true);
            startActivity(aIntent);
        }

        //跳转到搜索页面
        Intent aIntent = new Intent(this,ResultActivity.class);
        aIntent.putExtra("searchKey",mClearEditText.getText().toString().trim());
        aIntent.putExtra("searchType",1);
        startActivity(aIntent);
    }
}
