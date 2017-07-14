package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.OrderTabAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.DataPage;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModelZFB;
import com.bc.caibiao.model.Order;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.utils.NetUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;
import com.bc.caibiao.wxapi.AllPayRequestUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyOrderActivity extends BaseActivity {
    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.ivMemberLogo)
    SimpleDraweeView mUserIcon;

    @Bind(R.id.tvNickName)
    TextView mNickName;

    private int currentPage = 1;
    private ArrayList<Order> mOrderList = new ArrayList<>();
    OrderTabAdapter mOrderTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        loadData();
    }

    /*
        * 初始化布局
        * */
    private void initView() {
        mContainerLayout = (RelativeLayout) findViewById(R.id.container_relativelayout);
        mNickName = (TextView) findViewById(R.id.tvNickName);
        mUserIcon = (SimpleDraweeView) findViewById(R.id.ivMemberLogo);
        mNickName.setText("昵称: " + getMember().getMemberName());
        if (getMember().getPortrait() != null) {
            ImageLoader.progressiveLoad(getMember().getPortrait(), mUserIcon);
        }
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadData();
            }
        });
    }

    private void loadData() {
        BCHttpRequest.getOrderInterface().getOrderListApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)), String.valueOf(currentPage))
                .compose(HttpResponseHelper.<DataPage<Order>>getAllData())
                .subscribe(new Observer<DataPage<Order>>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onNext(DataPage<Order> orderBean) {
                        mPtrFrameLayout.stopPtrRefresh();

                        if (currentPage == 1) {
                            mOrderList.clear();
                        }
                        mOrderList.addAll(orderBean.getData());

                        loadAdapter();

                        if (orderBean.getData().size() < 10) {
                            mOrderTabAdapter.canLoadMore(false);
                        } else {
                            mOrderTabAdapter.canLoadMore(true);
                        }
                    }
                });
    }


    /*
    * 加载数据
    * */
    private void loadAdapter() {
        if (mOrderTabAdapter == null) {
            mOrderTabAdapter = new OrderTabAdapter(MyOrderActivity.this, mOrderList);
            mPtrFrameLayout.setAdapter(mOrderTabAdapter);
            mOrderTabAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {
                @Override
                public void OnLoadMore() {
                    currentPage++;
                    loadData();
                }
            });
            mOrderTabAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent aIntent = new Intent(MyOrderActivity.this, OrderDetailActivity.class);
                    aIntent.putExtra("orderId", mOrderList.get(arg2).getOrder_id());
                    startActivity(aIntent);
                }
            });
        } else {
            mOrderTabAdapter.setData(mOrderList);
            mOrderTabAdapter.notifyDataSetChanged();
        }
    }

    public void payWithZFB(int orderId) {
        BCHttpRequest.getOrderInterface().payOrderProductZFB(
                String.valueOf(orderId),
                Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)),
                "alipay",
                NetUtil.getWifiIp(this))
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
                            AllPayRequestUtils.alipay(MyOrderActivity.this, result.data, mHandler);
                        } else {
                            ToastUtils.showShort(MyOrderActivity.this, result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }


    public void payWithWX(int orderId) {
        BCHttpRequest.getOrderInterface().payOrderlProductWX(
                String.valueOf(orderId),
                Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)),
                "weixin",
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
                        } else {
                            ToastUtils.showShort(
                                    MyOrderActivity.this,
                                    result.fieldErrors.get(0).getMessage());
                        }
                    }
                });

    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
