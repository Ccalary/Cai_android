package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.MyTaskAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.MyTaskList;
import com.bc.caibiao.model.HomePageModel.PlaygroundTask;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.ui.qiming.TaskDetailActivity;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyMasterTaskActivity extends BaseActivity {

    PtrFrameLayout mPtrFrameLayout;
    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.empty_img)
    ImageView mEmptyImg;

    int currentPage = 1;
    MyTaskAdapter mAdapter;
    ArrayList<PlaygroundTask> mMyTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_master_task);
        initView();
        loadData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {
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
    }

    private void loadData(){
        BCHttpRequest.getQiMingInterface().getMyTaskApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),"0",currentPage+"",10+"","2")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyTaskList>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onNext(MyTaskList taskPriceList) {

                        mPtrFrameLayout.stopPtrRefresh();

                        if(!"0".equals(taskPriceList.state) && taskPriceList.fieldErrors.size()>0){
                            ToastUtils.showShort(MyMasterTaskActivity.this,taskPriceList.fieldErrors.get(0).getMessage());
                            return;
                        }

                        if(currentPage == 1){
                            mMyTasks.clear();
                        }

                        mMyTasks.addAll(taskPriceList.data.data);

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
            mAdapter = new MyTaskAdapter(this, mMyTasks,"3");
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {

                @Override
                public void OnLoadMore() {
                    currentPage++;
                    loadData();
                }
            });
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    Intent aIntent = new Intent(MyMasterTaskActivity.this,MasterTaskDetailAct.class);
                    aIntent.putExtra("taskID",mMyTasks.get(arg2).taskId);
                    startActivity(aIntent);

                }
            });
        } else {
            mAdapter.setData(mMyTasks);
            mAdapter.notifyDataSetChanged();
        }
    }
}
