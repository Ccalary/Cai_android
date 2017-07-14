package com.bc.caibiao.ui.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.HistroiesAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.Order;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import rx.Observer;

import static com.alipay.sdk.app.statistic.c.G;

public class OrderDetailActivity extends BaseActivity {
    /*UI*/
    private TextView mTvTitleStatus;
    private TextView mTvePayType;
    private TextView mTvOrdetTime;
    private TextView mTvOrderName;
    private TextView mTvOrderAddress;

    private TextView mTvOrderId;
    private TextView mTvOrderStatus;
    private TextView mTvOrderCompanyName;
    private TextView mTvOrderMoney;
    private SimpleDraweeView mPayIcon;
    private SimpleDraweeView mMarkIcon;
    private RecyclerView mRecyclerView;
    private HistroiesAdapter mHistoriesAdapter;
    private Order mOrderDetail;
    private String mOrderId;//订单Id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initView();
    }

    /*
    * 初始化布局
    * */
    private void initView() {
        mTvTitleStatus = (TextView) findViewById(R.id.order_status_title);
        mTvePayType = (TextView) findViewById(R.id.order_pay_type);
        mTvOrdetTime = (TextView) findViewById(R.id.order_time);
        mTvOrderName = (TextView) findViewById(R.id.order_people);
        mTvOrderAddress= (TextView) findViewById(R.id.order_address);
        mTvOrderId = (TextView) findViewById(R.id.order_id);
        mTvOrderStatus = (TextView) findViewById(R.id.order_status);
        mTvOrderCompanyName = (TextView) findViewById(R.id.order_des);
        mTvOrderMoney = (TextView) findViewById(R.id.order_money);
        mPayIcon = (SimpleDraweeView) findViewById(R.id.sdv_payicon);
        mMarkIcon = (SimpleDraweeView) findViewById(R.id.order_icon);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_rewardTask);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mOrderId = String.valueOf(getIntent().getExtras().getInt("orderId"));
        loadData();
    }

    private void loadData() {
        BCHttpRequest.getOrderInterface().getOrderDetailApi(mOrderId, Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)))
                .compose(HttpResponseHelper.<Order>getAllData())
                .subscribe(new Observer<Order>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(OrderDetailActivity.this, "网络错误");
                    }

                    @Override
                    public void onNext(Order order) {
                        mOrderDetail = order;
                        refreshUI();
                        loadAdapter();
                    }
                });
    }

    //刷新UI
    private void refreshUI() {
        mTvTitleStatus.setText(mOrderDetail.getOrder_status());
        mTvePayType.setText("付款方式: " + mOrderDetail.getPayment_method());
        mTvOrdetTime.setText("订单日期: " + mOrderDetail.getDate_added());
        mTvOrderName.setText(mOrderDetail.getName());
        mTvOrderAddress.setText(mOrderDetail.getPayment_address());
        mTvOrderId.setText("订单号ID: " + mOrderDetail.getOrder_number());
        mTvOrderStatus.setText(mOrderDetail.getOrder_status());
        mTvOrderCompanyName.setText(mOrderDetail.getProducts().get(0).getName());
        mTvOrderMoney.setText(mOrderDetail.getProducts().get(0).getPrice());
        if (mOrderDetail.getProducts().get(0).getThumb() != null) {
            ImageLoader.progressiveLoad(mOrderDetail.getProducts().get(0).getThumb(), mMarkIcon);
        } else {
            mMarkIcon.setBackgroundResource(R.drawable.home_banner_default);
        }
    }

    //加载历史交易记录
    private void loadAdapter() {
        if (mHistoriesAdapter == null) {
            mHistoriesAdapter = new HistroiesAdapter(OrderDetailActivity.this);
            mHistoriesAdapter.addList(mOrderDetail.getHistories());
            mRecyclerView.setAdapter(mHistoriesAdapter);
        } else {
            mHistoriesAdapter.clearList();
            mHistoriesAdapter.addList(mOrderDetail.getHistories());
            mHistoriesAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
