package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.CollectTradeMarkAdapter;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.BaseTestModel;
import com.bc.caibiao.model.CollectMarkModel;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.TrademarkFollow;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.ui.shangbiao.ResultActivity;
import com.bc.caibiao.ui.shangbiao.ShangbiaoDetailActivity;
import com.bc.caibiao.utils.MarkModuleUtil;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created  on 2017/4/18.
 * 收藏的商标列表
 */

public class CollectTradeMarkActivity extends BaseActivity {
    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    TextView title;

    private int currentPage = 1;
    private ArrayList<TrademarkFollow> mCollectrList = new ArrayList<>();
    private CollectTradeMarkAdapter mCollectTradeMarkAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collect_mark);
        mContainerLayout = (RelativeLayout) findViewById(R.id.container_relativelayout);
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadData();
            }
        });

        title=(TextView)findViewById(R.id.title);
        title.setText("您已关注"+"0/10商标");
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        loadData();
    }

    /**
     * 收藏列表
     * */
    private void loadData() {
        BCHttpRequest
                .getMarkInterface().getCollectTradeMarkListApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CollectMarkModel>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onNext(CollectMarkModel collectBean) {
                        mPtrFrameLayout.stopPtrRefresh();
                        if (currentPage == 1) {
                            mCollectrList.clear();
                        }
                        mCollectrList.addAll(collectBean.data.data);

                        title.setText("您已关注"+collectBean.data.totalCount+"/10商标");

                        setAdapter();

                        if (collectBean.data.data.size() < collectBean.data.pageSize) {
                            mCollectTradeMarkAdapter.canLoadMore(false);
                        } else {
                            mCollectTradeMarkAdapter.canLoadMore(true);
                        }
                    }
                });
    }

    /**
     * 取消收藏
     * */
    public void dismissFlow(final int index,String cxkey,String intcls){
        BCHttpRequest.getMarkInterface().dismissFollowMarkApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),cxkey,intcls)
                .compose(HttpResponseHelper.<BaseTestModel>getAllData())
                .subscribe(new ResolveFailSubscribe<BaseTestModel>(mContext, "处理中") {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        ToastUtils.showShort(CollectTradeMarkActivity.this,fieldError.getMessage());
                    }

                    @Override
                    protected void _onNext(BaseTestModel member) {
                        mCollectrList.remove(index);
                        mCollectTradeMarkAdapter.setData(mCollectrList);
                        mCollectTradeMarkAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void setAdapter() {
        if (mCollectTradeMarkAdapter == null) {
            mCollectTradeMarkAdapter = new CollectTradeMarkAdapter(CollectTradeMarkActivity.this, mCollectrList);
            mPtrFrameLayout.setAdapter(mCollectTradeMarkAdapter);
            mCollectTradeMarkAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {
                @Override
                public void OnLoadMore() {
                    currentPage++;
                    loadData();
                }
            });
            mCollectTradeMarkAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    //是否登录
                    if(!MarkModuleUtil.isLogin()){
                        Intent aIntent = new Intent(CollectTradeMarkActivity.this, LoginActivity.class);
                        startActivity(aIntent);
                        return;
                    }


                        Intent aIntent = new Intent(CollectTradeMarkActivity.this, ShangbiaoDetailActivity.class);
                        aIntent.putExtra("cxkeyNum",mCollectrList.get(position).getTrademark_regno());
                        aIntent.putExtra("intcls",mCollectrList.get(position).getTrademark_intcls());
                        startActivity(aIntent);
                }
            });
        } else {
            mCollectTradeMarkAdapter.setData(mCollectrList);
            mCollectTradeMarkAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
