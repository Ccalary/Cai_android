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
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;
import com.bc.caibiao.view.TopBarLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImMasterActivity extends BaseActivity {

    @Bind(R.id.id_tablayout)
    TopBarLayout mTopBarLayout;

    @Bind(R.id.empty_img)
    ImageView mEmptyImg;

    PtrFrameLayout mPtrFrameLayout;
    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    int currentPage = 1;
    MyTaskAdapter mAdapter;
    ArrayList<PlaygroundTask> mMyTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_master_task);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private void initView() {
        mTopBarLayout.setTitle("我是大师");

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
        BCHttpRequest.getQiMingInterface().getImMasterList(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),currentPage+"",10+"")
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
//                            ToastUtils.showShort(ImMasterActivity.this,taskPriceList.fieldErrors.get(0).getMessage());
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
            mAdapter = new MyTaskAdapter(this, mMyTasks,"4");
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
                    Intent aIntent = new Intent(ImMasterActivity.this,DoSignInfoAct.class);
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
