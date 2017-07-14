package com.bc.caibiao.ui.shangbiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.HomeShangBiaoAdapter;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.model.MarkModel.Advertise;
import com.bc.caibiao.model.MarkModel.AdvertiseList;
import com.bc.caibiao.model.MarkModel.ProductDetail;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.SimpleOutLinkAct;
import com.bc.caibiao.ui.qiming.TaskDetailActivity;
import com.bc.caibiao.view.PtrFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/21.
 */
public class ShangbiaoFragment extends BaseFragment {

    ArrayList<Advertise> mAdvertises = new ArrayList<>();

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;
    PtrFrameLayout mPtrFrameLayout;

    ShangbiaoHomeBanner mHomeBannerView;

    HomeShangBiaoAdapter mHomeRewardAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.fragment_shangbiao_new, container, false);
            ButterKnife.bind(this,mView);
        }
        isCreateView = true;
        return mView;
    }

    @Override
    protected void initView() {
        super.initView();
        mPtrFrameLayout = new PtrFrameLayout(getActivity());
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });

        mHomeBannerView = new ShangbiaoHomeBanner(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        super.initData();
        requestData();
    }

    /**
     * 获取首页数据
     * */
    private void requestData(){
        BCHttpRequest.getMarkInterface().markHomeProductListNewApi("3")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AdvertiseList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AdvertiseList advertiseList) {
                        mAdvertises.clear();
                        mAdvertises.addAll(advertiseList.data);
                        loadAdapter();
                    }
                });
    }

    private void loadAdapter(){
        if (mHomeRewardAdapter == null) {
            mHomeRewardAdapter = new HomeShangBiaoAdapter(getActivity(),mAdvertises);
            mHomeRewardAdapter.setHeaderView(mHomeBannerView.getView());
            mPtrFrameLayout.setAdapter(mHomeRewardAdapter);
            mHomeRewardAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    Advertise advertise = mAdvertises.get(arg2);

                    if("-8".equals(advertise.adType)){
                        return;
                    }else if("-9".equals(advertise.adType)){
                        if(!TextUtils.isEmpty(advertise.adLink)){
                            Intent aIntent = new Intent(getActivity(), SimpleOutLinkAct.class);
                            aIntent.putExtra("openUrl",advertise.adType);
                            aIntent.putExtra("title",advertise.adName);
                            startActivity(aIntent);
                        }
                    }else if("1".equals(advertise.adType)){
                        Intent aIntent = new Intent(getActivity(),TaskDetailActivity.class);
                        aIntent.putExtra("taskID",advertise.refEntityId);
                        getActivity().startActivity(aIntent);
                    }else if("2".equals(advertise.adType)){
                        //跳转到大师任务详情

                    }else if("6".equals(advertise.adType)){
                        //跳转到商标详情
                        Intent detailIntent=new Intent(getActivity(),ShangpingDetailActivity.class);
                        detailIntent.putExtra("productId",advertise.refEntityId);
                        startActivity(detailIntent);
                    }
                }
            });
            mHomeRewardAdapter.canLoadMore(false);
        } else {
            mHomeRewardAdapter.setData(mAdvertises);
            mHomeRewardAdapter.notifyDataSetChanged();
        }
    }




}
