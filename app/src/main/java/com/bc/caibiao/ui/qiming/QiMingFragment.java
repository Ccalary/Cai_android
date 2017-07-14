package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.HomeRewardAdapter;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.model.HomePageModel.HomePageModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ProgressSubscribe;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.view.HomeBannerView;
import com.bc.caibiao.view.NoRefreshlayout;
import com.bc.caibiao.view.TopBarLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QiMingFragment extends BaseFragment {

    HomePageModel mHomePageModel;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;
    NoRefreshlayout mPtrFrameLayout;

    HomeBannerView mHomeBannerView;

    HomeRewardAdapter mHomeRewardAdapter;

    @Bind(R.id.id_tablayout)
    TopBarLayout topLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!BaseApplication.isAddedFloatView){
            BaseApplication.getInstance().addFloatView();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.fragment_qiming_home, container, false);
            ButterKnife.bind(this,mView);
        }
        isCreateView = true;
        return mView;
    }

    @Override
    protected void initView() {
        super.initView();
        mPtrFrameLayout = new NoRefreshlayout(getActivity());
        mContainerLayout.addView(mPtrFrameLayout.getView());

        mHomeBannerView = new HomeBannerView(getActivity());

        BaseApplication.getInstance().addFloatView();

        topLayout.setAlpha(0);
        mPtrFrameLayout.getRecyclerView().setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollDistance =  mPtrFrameLayout.getScollYDistance();
                int checkDistance = AppUtil.dip2px(getActivity(),50);

                if(scrollDistance<checkDistance){
                    if(scrollDistance<10){
                        topLayout.setAlpha(0);
                    }else{
                        float procent = (float) ((scrollDistance*1.0)/(checkDistance*1.0));
                        topLayout.setAlpha(procent);
                    }
                }else{
                    topLayout.setAlpha(1);
                }
            }
        });
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
        BCHttpRequest.getQiMingInterface().getQimingHomeDataApi(
                3,10
        ).compose(HttpResponseHelper.<HomePageModel>getAllData())
                .subscribe(new ProgressSubscribe<HomePageModel>(mContext) {
                    @Override
                    protected void _onNext(HomePageModel homePageModel) {
                        mHomePageModel = homePageModel;
                        loadData();
                    }
                });
    }

    /**
     * */
    private void loadData(){
        mHomeBannerView.setViewData(mHomePageModel);
        loadAdapter();
    }

    private void loadAdapter(){
        if (mHomeRewardAdapter == null) {
            mHomeRewardAdapter = new HomeRewardAdapter(getActivity(),mHomePageModel.listXuanShangTasks);
            mHomeRewardAdapter.setHeaderView(mHomeBannerView.getView());
            mPtrFrameLayout.setAdapter(mHomeRewardAdapter);
            mHomeRewardAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent aIntent = new Intent(getActivity(),TaskDetailActivity.class);
                    aIntent.putExtra("taskID",mHomePageModel.listXuanShangTasks.get(arg2).taskId);
                    getActivity().startActivity(aIntent);
                }
            });
            mHomeRewardAdapter.canLoadMore(false);
        } else {
            mHomeRewardAdapter.setData(mHomePageModel.listXuanShangTasks);
            mHomeRewardAdapter.notifyDataSetChanged();
        }
    }
}
