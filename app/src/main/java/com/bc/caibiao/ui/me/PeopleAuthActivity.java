package com.bc.caibiao.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.MemberAuthApply;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ImageLoad;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 投稿人认证（只读）
 */
public class PeopleAuthActivity extends BaseActivity {

    MemberAuthApply apply;
    private android.widget.TextView tvName;
    private android.widget.TextView tvArea;
    private android.widget.TextView tvGoodAt;
    private com.facebook.drawee.view.SimpleDraweeView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_auth);
        apply = (MemberAuthApply) getIntent().getSerializableExtra("MemberAuthApply");
        this.ivPhoto = (SimpleDraweeView) findViewById(R.id.ivPhoto);
        this.tvGoodAt = (TextView) findViewById(R.id.tvGoodAt);
        this.tvArea = (TextView) findViewById(R.id.tvArea);
        this.tvName = (TextView) findViewById(R.id.tvName);

        ImageLoad.loadURL(ivPhoto,apply.getOriginal1());
        tvName.setText(apply.getTrueName());
        tvArea.setText(apply.getProvinceName()+"  "+apply.getCityName());
        tvGoodAt.setText(apply.getAttr1());
    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
