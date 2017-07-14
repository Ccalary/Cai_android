package com.bc.caibiao.ui.me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.MyTaskAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.MyTaskList;
import com.bc.caibiao.model.HomePageModel.PlaygroundTask;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.ui.qiming.TaskDetailActivity;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
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
@SuppressLint("ValidFragment")
public class AllTAskFragment extends BaseFragment{

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;
    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.empty_img)
    ImageView mEmptyImg;

    int currentPage = 1;
    int searchMode = 1;

    MyTaskAdapter mAdapter;
    ArrayList<PlaygroundTask> mMyTasks = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public AllTAskFragment(int type){
        searchMode = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.fragment_base_recycleer, container, false);
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
    }


    public void refreshData(){
        if(mMyTasks.isEmpty()){
            requestData();
        }
    }


    private void requestData(){
        BCHttpRequest.getQiMingInterface().getMyTaskApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),searchMode+"",currentPage+"",10+"","1")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyTaskList>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                        if(mMyTasks.isEmpty()){
                            mEmptyImg.setVisibility(View.VISIBLE);
                        }else{
                            mEmptyImg.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onNext(MyTaskList taskPriceList) {

                        mPtrFrameLayout.stopPtrRefresh();

                        if(!"0".equals(taskPriceList.state) && taskPriceList.fieldErrors.size()>0){
                            ToastUtils.showShort(getActivity(),taskPriceList.fieldErrors.get(0).getMessage());
                            return;
                        }

                        if(currentPage == 1){
                            mMyTasks.clear();
                        }

                        mMyTasks.addAll(taskPriceList.data.data);

                        if(mMyTasks.isEmpty()){
                            mEmptyImg.setVisibility(View.VISIBLE);
                        }else{
                            mEmptyImg.setVisibility(View.GONE);
                        }

                        loadAdapter();

                        if(taskPriceList.data.data.size() < 10){
                            mAdapter.canLoadMore(false);
                        }else{
                            mAdapter.canLoadMore(true);
                        }
                    }
                });
    }


    private void loadAdapter(){
        if (mAdapter == null) {
            mAdapter = new MyTaskAdapter(getActivity(), mMyTasks,"1");
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {

                @Override
                public void OnLoadMore() {
                    currentPage++;
                    requestData();
                }
            });
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent aIntent = new Intent(getActivity(),TaskDetailActivity.class);
                    aIntent.putExtra("taskID",mMyTasks.get(arg2).taskId);
                    startActivity(aIntent);

                }
            });
        } else {
            mAdapter.setData(mMyTasks);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
