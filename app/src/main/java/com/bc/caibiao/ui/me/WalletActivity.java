package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.WalletAdapter;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.AccountFlowModel;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.MemberAccount;
import com.bc.caibiao.model.MemberAccountFlow;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observer;

import static com.bc.caibiao.R.drawable.icon_fucha;

/**
 * Created by Austriee on 2017/4/22.
 */

public class WalletActivity extends BaseActivity {
    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.tvRight)
    TextView mTvRight;


    private int currentPage = 1;
    private WalletAdapter walletAdapter;
    private TextView mAccountBalance;
    private MemberAccount mMemberAccount;
    private ArrayList<MemberAccountFlow> mAccountFlowArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initView();
    }

    @Override
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tvRight:
                Intent recordIntent = new Intent(WalletActivity.this, WalletWithdrawalsActivity.class);
                if(mMemberAccount != null){
                    recordIntent.putExtra("AccountBalanceYuan", mMemberAccount.getAccountBalanceYuan());
                    recordIntent.putExtra("AccountBalance", mMemberAccount.getAccountBalance());
                }
                startActivity(recordIntent);
                break;
        }
    }

    private void initView() {
        mTvRight = (TextView) findViewById(R.id.tvRight);
        mTvRight.setOnClickListener(this);

        mContainerLayout = (RelativeLayout) findViewById(R.id.container_relativelayout);
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                requestBalanceData();
                requestAccountFlow();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        requestBalanceData();
        requestAccountFlow();
    }

    /*
    * 请求余额
    * */
    private void requestBalanceData() {
        BCHttpRequest.getAccountInterface().searchAccountInfoApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)))
                .compose(HttpResponseHelper.<MemberAccount>getAllData())
                .subscribe(new ResolveFailSubscribe<MemberAccount>(mContext) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        //ToastUtils.showShort(WalletActivity.this,fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(MemberAccount memberAccount) {
                        dismissLoading();
                        mMemberAccount = memberAccount;
                    }
                });
    }

    /*
       * 请求帐号流水
       * */
    private void requestAccountFlow() {
        BCHttpRequest.getAccountInterface().searchAccountFlowsApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)), currentPage, Constant.PAGESIZE)
                .compose(HttpResponseHelper.<AccountFlowModel>getAllData())
                .subscribe(new ResolveFailSubscribe<AccountFlowModel>(mContext) {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        mPtrFrameLayout.stopPtrRefresh();
                        ToastUtils.showShort(WalletActivity.this, fieldError.getMessage());
                        setAdapter();
                    }

                    @Override
                    protected void _onNext(AccountFlowModel accountFlowModel) {
                        mPtrFrameLayout.stopPtrRefresh();
                        if (currentPage == 1) {
                            mAccountFlowArrayList.clear();
                        }

                        mAccountFlowArrayList.addAll(accountFlowModel.data);

                        setAdapter();

                        if (accountFlowModel.data.size() < Constant.PAGESIZE) {
                            walletAdapter.canLoadMore(false);
                        } else {
                            walletAdapter.canLoadMore(true);
                        }
                    }
                });
    }

    private void setAdapter() {
        if (walletAdapter == null) {
            walletAdapter = new WalletAdapter(WalletActivity.this, mAccountFlowArrayList);
            View hearderView = LayoutInflater.from(mContext).inflate(R.layout.wallet_header, null);
            mAccountBalance = (TextView) hearderView.findViewById(R.id.account_balance);
            walletAdapter.setHeaderView(hearderView);
            walletAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.wallet_footer, null));
            mPtrFrameLayout.setAdapter(walletAdapter);
            walletAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {
                @Override
                public void OnLoadMore() {
                    currentPage++;
                    requestAccountFlow();
                }
            });
        } else {
            walletAdapter.setData(mAccountFlowArrayList);
            walletAdapter.notifyDataSetChanged();
        }

        if (mMemberAccount != null) {
            mAccountBalance.setText(" ¥ " + String.valueOf(mMemberAccount.getAccountBalanceYuan()));
        } else {
            mAccountBalance.setText(" ¥ 0");
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
