package com.bc.caibiao.ui.qiming;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModelZFB;
import com.bc.caibiao.model.MarkModel.TagList;
import com.bc.caibiao.model.MarkModel.TaskTag;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.NetUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.wxapi.AllPayRequestUtils;
import com.bc.caibiao.wxapi.PayEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bc.caibiao.wxapi.WXPayEntryActivity.PAYSUCCESS;

/**
 * Created by chengyanfang on 2017/4/20.
 */

public class SearchServiceAct extends BaseActivity {

    @Bind(R.id.etTaskTitle)
    EditText mTitle;

    @Bind(R.id.pay_type_group)
    RadioGroup mPayTypeGroup;

    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Bind(R.id.tip_tv)
    TextView tvTip;

    //支付方式：1.支付宝、2、微信
    int payType = -1;

    TaskTag mTaskTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaxun_center_layout);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {
        mPayTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.zfb_pay){
                    payType = 1;
                }else{
                    payType = 2;
                }
            }
        });

        mTitle = (EditText)findViewById(R.id.etTaskTitle);
        findViewById(R.id.tvPublish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToCheck();
            }
        });

        if(getIntent() != null) {
            mTitle.setText(getIntent().getStringExtra("ShangBiaoName"));
        }
    }

    private void initData(){
        //请求标签
        BCHttpRequest.getQiMingInterface().getCheckPriceListApi("dg_recheck_price")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TagList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TagList tagList) {
                        if(tagList.data.size()>0){
                            mTaskTag = tagList.data.get(0);
                            tvPrice.setText("¥"+mTaskTag.itemContent);
                            tvTip.setText("人工商标复查费用为"+mTaskTag.itemContent+"元，并出具检索报告，8点至17点，1小时内出具检索报告");
                        }

                    }
                });
    }


    private void onClickToCheck(){
        if(TextUtils.isEmpty(mTitle.getText().toString().trim())){
            ToastUtils.showShort(this,"请输入商标名称");
            return;
        }

        if(payType == -1){
            ToastUtils.showShort(this,"请选择支付方式");
        }

        showProgressHUD(this, "申请中");

        if(payType == 1){
            payWithZFB();
        }else{
            payWithWX();
        }
    }

    private String getPriceFen(){
        float priceFen = 0;
        try{
            priceFen = Float.parseFloat(mTaskTag.itemContent)*100;
        }catch (Exception e){

        }
        return ((int)priceFen) +"";
    }

    private void payWithZFB(){
        BCHttpRequest.getQiMingInterface().checkBrandWZFB(
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                SP.getInstance().getString(SPTag.TAG_MEMBER_NAME),
                "1",
                getPriceFen(),
                mTitle.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishTaskResultModelZFB>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(PublishTaskResultModelZFB result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {
                            AllPayRequestUtils.alipay(SearchServiceAct.this, result.data, mHandler);
                        }
                        else {
                            ToastUtils.showShort(SearchServiceAct.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }


    private void payWithWX(){
        BCHttpRequest.getQiMingInterface().checkBrandWX(
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                SP.getInstance().getString(SPTag.TAG_MEMBER_NAME),
                "2",
                getPriceFen(),
                mTitle.getText().toString().trim(),
                NetUtil.getWifiIp(this))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PublishTaskResultModel>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(PublishTaskResultModel result) {
                        dismissProgressHUD();
                        if (result.fieldErrors.isEmpty()) {
                            weiXinPay(result);
                        }
                        else {
                            ToastUtils.showShort(
                                    SearchServiceAct.this,
                                    result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }


    /****************************支付回调****************************/
    @Override
    protected void onAlipaySuccess() {
        super.onAlipaySuccess();
        this.finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPublicStringEvent(PayEvent event) {
        if (PAYSUCCESS.equals(event.eventTitle)){
            this.finish();
        }
    }


}
