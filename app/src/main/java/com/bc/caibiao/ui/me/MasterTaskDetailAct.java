package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.DoSignInfoAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.BlankModel;
import com.bc.caibiao.model.HomePageModel.TaskDetailModel;
import com.bc.caibiao.model.HomePageModel.TouGaoer;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;
import com.bc.caibiao.view.popupwindow.PXFAlterView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chengyanfang on 2017/4/22.
 */

public class MasterTaskDetailAct extends BaseActivity {

    PtrFrameLayout mPtrFrameLayout;
    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    String mTaskId;
    DoSignInfoAdapter mAdapter;
    TaskDetailModel mTaskDetailModel;
    DoSignHeaderView mHeaderView;
    ArrayList<TouGaoer> mTougaoerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashi_task_detail_other);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mContainerLayout = (RelativeLayout) findViewById(R.id.container_relativelayout);

        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());

        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {

            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mHeaderView = new DoSignHeaderView(this);
    }


    private void initData(){
        mTaskId = getIntent().getStringExtra("taskID");
        loadData();
    }

    private void loadData(){
        BCHttpRequest.getQiMingInterface().getDashiTaskDetail(mTaskId, SP.getInstance().getString(SPTag.TAG_MEMBER_ID))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskDetailModel>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onNext(TaskDetailModel taskDetailModel) {
                        mTaskDetailModel = taskDetailModel;
                        mTougaoerList.clear();
                        mTougaoerList.addAll(taskDetailModel.data.taskTouGaoList);
                        loadUI();
                    }
                });
    }

    private void loadUI(){
        mHeaderView.setViewData(mTaskDetailModel.data);
        mHeaderView.setDashiInfo(mTaskDetailModel.data);
        setAdapter();
    }


    private void setAdapter(){
        if(mAdapter == null){
            mAdapter =  new DoSignInfoAdapter(this,mTougaoerList,false);
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.setHeaderView(mHeaderView.getView());
            mAdapter.mDoCallCallback = new DoSignInfoAdapter.DoCallCallback() {
                @Override
                public void doCallback(final int index) {
                    new PXFAlterView(MasterTaskDetailAct.this, "提问", "请输入提问内容", new PXFAlterView.EditContentCallBack() {
                        @Override
                        public void onCompleteEdit(String content) {
                            requestQuestion(index,content);
                        }
                    },true).show();
                }
            };


            mAdapter.mBuyTaskCallback = new DoSignInfoAdapter.BuyTaskCallback() {
                @Override
                public void toBuyTask(int index) {
                    forwardToBuyBorrow(index);
                }
            };
        }else{
            mAdapter.notifyDataSetChanged();
        }

    }



    /**
     * 前去注册商标
     * */
    private void forwardToBuyBorrow(int index){
        Intent intent = new Intent(this, ZhuCeShangBiaoActivity.class);
        startActivity(intent);
    }

    /**
     * 请求回复
     * **/
    private void requestQuestion(int index,String content){
        BCHttpRequest.getQiMingInterface().askOrAnswerTouGaoApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),"1",content,mTougaoerList.get(index).touGaoId)
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
                                ToastUtils.showShort(MasterTaskDetailAct.this,taskDetailModel.fieldErrors.get(0).getMessage());
                            }
                        }else{
                            ToastUtils.showShort(MasterTaskDetailAct.this,"提问成功");
                            loadData();
                        }
                    }
                });
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
