package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.BrandRecheck;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ImageLoad;
import com.bc.caibiao.utils.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import rx.Observer;

/**
 * Created  on 2017/4/19.
 * 复查详情
 */

public class RecheckDetailActivity extends BaseActivity {
    /**
     * UI元素
     */
    //注册商标
    @Bind(R.id.recheck_zcsb)
    LinearLayout mRecheckZCSB;

    //商标名称
    @Bind(R.id.recheck_markname)
    TextView mRecheckMarkName;

    //复检员
    @Bind(R.id.recheck_people)
    TextView mRecheckPeople;

    //检测报告
    @Bind(R.id.recheck_result)
    TextView mRecheckResult;

    //图标
    @Bind(R.id.rechek_market_icon)
    SimpleDraweeView mRecheckIcon;

    //复查商品id
    private int mRecheckId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recheck_detail);
        mRecheckId = getIntent().getExtras().getInt("recheckId");
        initView();
    }

    private void initView() {
        mRecheckMarkName = (TextView) findViewById(R.id.recheck_markname);
        mRecheckPeople = (TextView) findViewById(R.id.recheck_people);
        mRecheckResult = (TextView) findViewById(R.id.recheck_result);
        mRecheckIcon = (SimpleDraweeView) findViewById(R.id.rechek_market_icon);
        mRecheckZCSB = (LinearLayout) findViewById(R.id.recheck_zcsb);
        mRecheckZCSB.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestRecheckDetailData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recheck_zcsb:
                Intent intent = new Intent(this, ZhuCeShangBiaoActivity.class);
                startActivity(intent);
                break;
        }
    }

    /*
        * 复查详情
        * */
    private void requestRecheckDetailData() {
        BCHttpRequest.getOtherInterface().getBrandRecheckDetailApi(mRecheckId)
                .compose(HttpResponseHelper.<BrandRecheck>getAllData())
                .subscribe(new Observer<BrandRecheck>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(BrandRecheck brandRecheck) {
                        dismissLoading();
                        mRecheckMarkName.setText(brandRecheck.getBrandName());
                        mRecheckPeople.setText(brandRecheck.getHandlerName());
                        mRecheckResult.setText(brandRecheck.getResultText());
                        if (brandRecheck.getResultPic() != null) {
                            //ImageLoader.progressiveLoad(URLConfig.baseUrl_pic_oss + brandRecheck.getResultPic(), mRecheckIcon);
                            ImageLoad.loadURL(mRecheckIcon, brandRecheck.getResultPic());
                        }
                    }
                });
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
