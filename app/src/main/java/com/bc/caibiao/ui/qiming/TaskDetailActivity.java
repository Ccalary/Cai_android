package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.DoSignInfoAdapter;
import com.bc.caibiao.adapter.QiMingModule.SendPostFooterview;
import com.bc.caibiao.adapter.QiMingModule.TaskMyCreateAdapter;
import com.bc.caibiao.adapter.QiMingModule.TaskSenderAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.HomePageModel.TaskDetailInnerModel;
import com.bc.caibiao.model.HomePageModel.TaskDetailModel;
import com.bc.caibiao.model.HomePageModel.TouGaoer;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.ui.me.DoSignInfoAct;
import com.bc.caibiao.ui.me.ZhuCeShangBiaoActivity;
import com.bc.caibiao.utils.MarkModuleUtil;
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
 * 任务详情
 */
public class TaskDetailActivity extends BaseActivity {

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;
    PtrFrameLayout mPtrFrameLayout;
    TaskDetailHeaderView mHeaderView;
    SendPostFooterview mFooterView;

    String mTaskId;

    TaskDetailInnerModel mTaskDetailInnerModel;

    ArrayList<TouGaoer> mTaskTouGaoList = new ArrayList<>();

    TaskSenderAdapter mAdapter;
    TaskMyCreateAdapter mAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        mHeaderView = new TaskDetailHeaderView(this);
        mFooterView = new SendPostFooterview(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendPost();
            }
        });
    }

    private void initData(){
        mTaskId = getIntent().getStringExtra("taskID");
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    /**
     *
     * */
    private void requestData(){
        showProgressHUD(this,"加载中");

        BCHttpRequest.getQiMingInterface().getTaskDetail(mTaskId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskDetailModel>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(TaskDetailModel taskDetailModel) {

                        if("0".equals(taskDetailModel.state)){
                            mPtrFrameLayout.stopPtrRefresh();
                            mTaskDetailInnerModel = taskDetailModel.data;
                            mTaskTouGaoList.clear();
                            mTaskTouGaoList.addAll(taskDetailModel.data.taskTouGaoList);
                            loadData();
                        }
                        dismissProgressHUD();
                    }
                });

    }

    /**
     * 现实数据
     * */
    private void loadData(){
        mHeaderView.setData(mTaskDetailInnerModel);

        if(mTaskDetailInnerModel.creatorId.equals(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))){
            loadAnotherAdapter();
        }else{
            loadAdapter();
        }
    }


    private void loadAdapter(){

        if(mAdapter == null){
            mAdapter = new TaskSenderAdapter(this,mTaskTouGaoList);
            mAdapter.setHeaderView(mHeaderView.getView());
            mAdapter.setFooterView(mFooterView.getView());
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                }
            });
            mAdapter.canLoadMore(false);
        }else{
            mAdapter.notifyDataSetChanged();
        }

    }

    private void loadAnotherAdapter(){

        if(mAdapter2 == null){
            mAdapter2 = new TaskMyCreateAdapter(this,mTaskTouGaoList,mTaskDetailInnerModel.isXuanShangFenQian);
            mAdapter2.setHeaderView(mHeaderView.getView());
            mPtrFrameLayout.setAdapter(mAdapter2);
            mAdapter2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                }
            });

            mAdapter2.mBuyTaskCallback = new DoSignInfoAdapter.BuyTaskCallback() {
                @Override
                public void toBuyTask(int index) {
                    forwardToBuyBorrow(index);
                }
            };

            mAdapter2.mAcceptTaskCallback = new TaskMyCreateAdapter.AcceptTaskCallback() {
                @Override
                public void toAcceptTask(int index) {
                    forwardToAccept(index);
                }
            };

            mAdapter2.canLoadMore(false);
        }else{
            mAdapter2.setIsXuanshangjieshu(mTaskDetailInnerModel.isXuanShangFenQian);
            mAdapter2.notifyDataSetChanged();
        }


    }


    private void onClickSendPost(){
        //是否登录
        if(!MarkModuleUtil.isLogin()){
            Intent aIntent = new Intent(this, LoginActivity.class);
            aIntent.putExtra("isNeedBack",true);
            startActivity(aIntent);
        }

        if(mTaskDetailInnerModel.creatorId.equals(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))){
            ToastUtils.showShort(this,"自己不能给自己投稿");
            return;
        }

        if("1".equals(mTaskDetailInnerModel.isXuanShangFenQian)){
            ToastUtils.showShort(this,"已经截稿");
            return;
        }

        Intent aIntent = new Intent(this,SendPostAct.class);
        aIntent.putExtra("taskId",mTaskId);
        startActivity(aIntent);
    }

    /**
     * 前去注册商标
     * */
    private void forwardToBuyBorrow(int index){
        Intent intent = new Intent(this, ZhuCeShangBiaoActivity.class);
        startActivity(intent);
    }


    /**
     * 同意
     * */
    private void forwardToAccept(int index){

        showProgressHUD(this,"处理中...");

        BCHttpRequest.getQiMingInterface().acceptTouGao(mTaskTouGaoList.get(index).touGaoId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BlankModel>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressHUD();
                    }

                    @Override
                    public void onNext(BlankModel taskDetailModel) {
                        dismissProgressHUD();
                        if(!"0".equals(taskDetailModel.state)){
                            if(taskDetailModel.fieldErrors.size()>0){
                                ToastUtils.showShort(TaskDetailActivity.this,taskDetailModel.fieldErrors.get(0).getMessage());
                            }
                        }else{
                            ToastUtils.showShort(TaskDetailActivity.this,"成功采纳");
                            requestData();
                        }
                    }
                });
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
