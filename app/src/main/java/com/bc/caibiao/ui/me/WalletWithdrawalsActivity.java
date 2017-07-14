package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.CashApplyModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Austriee on 2017/4/22.
 */

public class WalletWithdrawalsActivity extends BaseActivity {
    @Bind(R.id.tvMoney)
    EditText mEtMoney;

    @Bind(R.id.etZhifubao)
    EditText mZfbAccount;

    @Bind(R.id.etName)
    EditText mZfbName;

    @Bind(R.id.tvRight)
    TextView mTvRight;


    @Bind(R.id.tvSubmit)
    TextView mTvSubmit;

    @Bind(R.id.tv_accountBalance)
    TextView mTvAccountBalance;


    private double mAccountBalanceYuan = 0.0;// 余额(元)
    private double mAccountBalance = 0.0;// 余额(分)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);
        if(getIntent() != null) {
            mAccountBalanceYuan = getIntent().getDoubleExtra("AccountBalanceYuan", 0.0);
            mAccountBalance = getIntent().getIntExtra("AccountBalance", 0);
        }
        initView();
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                requestBalanceData();
                break;
            case R.id.tvRight:
                Intent recordIntent = new Intent(WalletWithdrawalsActivity.this, WithdrawalsRecordActivity.class);
                startActivity(recordIntent);
                break;
        }
    }

    private void initView() {
        mEtMoney = (EditText) findViewById(R.id.tvMoney);
        mZfbAccount = (EditText) findViewById(R.id.etZhifubao);
        mZfbName = (EditText) findViewById(R.id.etName);
        mTvRight = (TextView) findViewById(R.id.tvRight);
        mTvSubmit = (TextView) findViewById(R.id.tvSubmit);
        mTvAccountBalance = (TextView) findViewById(R.id.tv_accountBalance);
        mTvAccountBalance.setText(" ¥ "  + mAccountBalanceYuan);
        mTvRight.setOnClickListener(this);
        mTvSubmit.setOnClickListener(this);
    }

    /*
     * 提现
     */
    private void requestBalanceData() {
        if (SP.getInstance().getString(SPTag.TAG_MEMBER_ID) == null || SP.getInstance().getString(SPTag.TAG_MEMBER_ID).equals("")) {
            ToastUtils.showShort(this, "请登录");
            return;
        }

        if (TextUtils.isEmpty(mEtMoney.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入金额");
            return;
        }

        if (TextUtils.isEmpty(mZfbAccount.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入支付宝帐号");
            return;
        }

        if (TextUtils.isEmpty(mZfbName.getText().toString().trim())) {
            ToastUtils.showShort(this, "请输入真实姓名");
            return;
        }

        String money = mEtMoney.getText().toString();
        float fRealMeney = Float.valueOf(money);
        int iRealMoney = (int) (fRealMeney * 100);

        if (iRealMoney > mAccountBalance) {
            ToastUtils.showShort(this, "超过了账户中的余额");
            return;
        }

        BCHttpRequest.getAccountInterface().addMemberWithdrawCashApplyApi(
                Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)),
                iRealMoney,
                mZfbAccount.getText().toString().trim(),
                mZfbName.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CashApplyModel>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(CashApplyModel cashApplyModel) {
                        dismissLoading();
                        if (cashApplyModel.state == 0) {
                            Intent recordIntent = new Intent(WalletWithdrawalsActivity.this, WithdrawalsRecordActivity.class);
                            startActivity(recordIntent);
                            finish();
                        } else {
                            ToastUtils.showShort(WalletWithdrawalsActivity.this, cashApplyModel.fieldErrors.get(0).getMessage());
                        }
                    }
                });
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
