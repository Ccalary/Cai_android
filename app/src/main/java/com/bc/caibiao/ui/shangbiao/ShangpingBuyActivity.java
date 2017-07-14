package com.bc.caibiao.ui.shangbiao;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModelZFB;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.SearchServiceAct;
import com.bc.caibiao.utils.NetUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.StringUtil;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.wxapi.AllPayRequestUtils;
import com.bc.caibiao.wxapi.PayEvent;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bc.caibiao.wxapi.WXPayEntryActivity.PAYSUCCESS;

public class ShangpingBuyActivity extends BaseActivity {


    @Bind(R.id.sdvPic)
    ImageView sdvPic;

    @Bind(R.id.cnt_tv)
    TextView tvName;

    @Bind(R.id.tvPrice)
    TextView tvPrice;

    @Bind(R.id.tvNum)
    TextView tvBuyCnt;

    @Bind(R.id.pay_type_group)
    RadioGroup mPayTypeGroup;

    @Bind(R.id.et_address)
    EditText etAddress;

    int basepriceFen = 0;
    int priceFen = 0;
    String cntType;
    String productId;

    int num = 1;

    //支付方式：1.支付宝、2、微信
    int payType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangping_buy);
        ButterKnife.bind(this);
        initView();
        initData();
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

        findViewById(R.id.tvDec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num > 1){
                    num = num -1;
                }
                tvBuyCnt.setText(num+"");
                refreshPriceFen();
            }
        });

        findViewById(R.id.tvInc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = num + 1;
                refreshPriceFen();
                tvBuyCnt.setText(num+"");
            }
        });

        findViewById(R.id.rlv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBuy();
            }
        });

    }

    private void initData(){
        Glide.with(this).load(getIntent().getStringExtra("imgPath")).placeholder(R.drawable.icon_guoji).into(sdvPic);
        cntType = getIntent().getStringExtra("cntType");
        productId = getIntent().getStringExtra("productId");
        priceFen = getIntent().getIntExtra("priceFen",0);
        basepriceFen = getIntent().getIntExtra("priceFen",0);
        tvName.setText("库存现状："+cntType);
        refreshPriceFen();
    }


    private void refreshPriceFen(){
        priceFen = basepriceFen * num;
        tvPrice.setText("¥"+ StringUtil.getFormatedFloatString((priceFen/100)+""));
    }


    private void doBuy(){
        if(payType == 1){
            payWithZFB();
        }else if (payType == 2){
            payWithWX();
        }else {
            ToastUtils.showShort(this,"选择支付方式");
        }
    }

    private void payWithZFB(){
        BCHttpRequest.getQiMingInterface().buyProductZFB(
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                productId,
                String.valueOf(num),
                NetUtil.getWifiIp(this),
                "alipay",etAddress.getText().toString())
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
                            AllPayRequestUtils.alipay(ShangpingBuyActivity.this, result.data, mHandler);
                        }
                        else {
                            ToastUtils.showShort(ShangpingBuyActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }


    private void payWithWX(){
        BCHttpRequest.getQiMingInterface().buyProductWX(
                SP.getInstance().getString(SPTag.TAG_MEMBER_ID),
                productId,
                String.valueOf(num),
                NetUtil.getWifiIp(this),
                "weixin")
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
                                    ShangpingBuyActivity.this,
                                    result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!BaseApplication.isAddedFloatView){
            BaseApplication.getInstance().addFloatView();
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseApplication.getInstance().removeFloatView();
    }

}
