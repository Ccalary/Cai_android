package com.bc.caibiao.ui.me;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.WithdrawalsRecordAdapter;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.CashAppliesModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.MemberWithdrawCashApply;
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

/**
 * Created by Austriee on 2017/4/22.
 */

public class WithdrawalsRecordActivity extends BaseActivity {
    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    private WithdrawalsRecordAdapter mWithdrawalsRecordAdapter;
    private int currentPage = 1;
    private ArrayList<MemberWithdrawCashApply> mRecordCashApplyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals_record);
        mContainerLayout = (RelativeLayout) findViewById(R.id.container_relativelayout);
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                requestCashApplies();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        requestCashApplies();
    }

    /*
       * 请求提现记录
       * */
    private void requestCashApplies() {
        BCHttpRequest.getAccountInterface().listWithdrawCashAppliesApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)), currentPage, Constant.PAGESIZE)
                .compose(HttpResponseHelper.<CashAppliesModel>getAllData())
                .subscribe(new ResolveFailSubscribe<CashAppliesModel>(mContext, "处理中") {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        ToastUtils.showShort(WithdrawalsRecordActivity.this, fieldError.getMessage());
                    }

                    @Override
                    public void _onNext(CashAppliesModel cashAppliesModel) {
                        mPtrFrameLayout.stopPtrRefresh();
                        if (currentPage == 1) {
                            mRecordCashApplyList.clear();
                        }
                        mRecordCashApplyList.addAll(cashAppliesModel.data);


                        setAdapter();

                        if (cashAppliesModel.data.size() < Constant.PAGESIZE) {
                            mWithdrawalsRecordAdapter.canLoadMore(false);
                        } else {
                            mWithdrawalsRecordAdapter.canLoadMore(true);
                        }
                    }
                });
    }

    private void setAdapter() {
        if (mWithdrawalsRecordAdapter == null) {
            mWithdrawalsRecordAdapter = new WithdrawalsRecordAdapter(WithdrawalsRecordActivity.this, mRecordCashApplyList);
            mPtrFrameLayout.setAdapter(mWithdrawalsRecordAdapter);
            mWithdrawalsRecordAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {
                @Override
                public void OnLoadMore() {
                    currentPage++;
                    requestCashApplies();
                }
            });
        } else {
            mWithdrawalsRecordAdapter.setData(mRecordCashApplyList);
            mWithdrawalsRecordAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
