package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.DoSignAct;
import com.bc.caibiao.adapter.QiMingModule.DoSignInfoAdapter;
import com.bc.caibiao.adapter.QiMingModule.SendPostFooterview;
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
import com.bc.caibiao.view.TopBarLayout;
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

public class DoSignInfoAct extends BaseActivity {

    @Bind(R.id.id_tablayout)
    TopBarLayout mTopBarLayout;

    PtrFrameLayout mPtrFrameLayout;
    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    String mTaskId;
    DoSignInfoAdapter mAdapter;
    TaskDetailModel mTaskDetailModel;
    DoSignHeaderView mHeaderView;
    SendPostFooterview mFooterView;


    ArrayList<TouGaoer> mTougaoerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashi_task_detail_other);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                loadData();
            }
        });
        mHeaderView = new DoSignHeaderView(this);
        mHeaderView.dismissDashiInfo();
        mFooterView = new SendPostFooterview(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aIntent = new Intent(DoSignInfoAct.this, DoSignAct.class);
                aIntent.putExtra("mTaskId",mTaskId);
                aIntent.putExtra("type","addNew");
                startActivity(aIntent);
            }
        },"起名");
    }


    private void initData(){
        mTaskId = getIntent().getStringExtra("taskID");
        loadData();
    }

    private void loadData(){
        BCHttpRequest.getQiMingInterface().getDashiTaskDetail(mTaskId,SP.getInstance().getString(SPTag.TAG_MEMBER_ID))
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

    /**
     * 加载界面
     * */
    private void loadUI(){
        mHeaderView.setViewData(mTaskDetailModel.data);
        setAdapter();
        if(mTougaoerList.size()>0){
            mFooterView.setTitle("再次起名");
        }
    }

    private void setAdapter(){
        if(mAdapter == null){
            mAdapter =  new DoSignInfoAdapter(this,mTougaoerList,true);
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.setHeaderView(mHeaderView.getView());
            mAdapter.setFooterView(mFooterView.getView());

            mAdapter.mRemoveCallback = new DoSignInfoAdapter.RemoveCallback() {
                @Override
                public void doRemoveAt(final int index) {
                    new PXFAlterView(DoSignInfoAct.this, "确认删除", "是否确认删除这条记录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rquestRemoveAt(index);
                        }
                    }).show();
                }
            };

            mAdapter.mEdtiCallback = new DoSignInfoAdapter.EdtiCallback() {
                @Override
                public void doEditAt(int index) {
                    rquestEditAt(index);
                }
            };

            mAdapter.mDoCallCallback = new DoSignInfoAdapter.DoCallCallback() {
                @Override
                public void doCallback(final int index) {
                    new PXFAlterView(DoSignInfoAct.this, "回复", "请输入回复内容", new PXFAlterView.EditContentCallBack() {
                        @Override
                        public void onCompleteEdit(String content) {
                            requestCallBack(index,content);
                        }
                    },true).show();
                }
            };

        }else{
            mAdapter.notifyDataSetChanged();
        }

    }


    /**
     * 请求删除
     * */
    private void rquestRemoveAt(final int index){
        showProgressHUD(this,"删除中...");

        BCHttpRequest.getQiMingInterface().deleteTouGaoApi(mTougaoerList.get(index).touGaoId)
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
                                ToastUtils.showShort(DoSignInfoAct.this,taskDetailModel.fieldErrors.get(0).getMessage());
                            }
                        }else{
                            ToastUtils.showShort(DoSignInfoAct.this,"删除成功");
                            mTougaoerList.remove(index);
                            setAdapter();
                        }
                    }
                });
    }


    /**
     * 请求回复
     * **/
    private void requestCallBack(int index,String content){
        BCHttpRequest.getQiMingInterface().askOrAnswerTouGaoApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID),"2",content,mTougaoerList.get(index).touGaoId)
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
                                ToastUtils.showShort(DoSignInfoAct.this,taskDetailModel.fieldErrors.get(0).getMessage());
                            }
                        }else{
                            ToastUtils.showShort(DoSignInfoAct.this,"回复成功");
                            loadData();
                        }
                    }
                });
    }


    /**
     * 请求编辑
     * */
    private void rquestEditAt(int index){
        Intent aIntent = new Intent(DoSignInfoAct.this, DoSignAct.class);
        aIntent.putExtra("mTaskId",mTaskId);
        aIntent.putExtra("type","edit");
        aIntent.putExtra("tougaoId",mTougaoerList.get(index).touGaoId);
        aIntent.putExtra("title",mTougaoerList.get(index).brandName);
        aIntent.putExtra("content",mTougaoerList.get(index).reason);
        aIntent.putStringArrayListExtra("picPaths",mTougaoerList.get(index).picPaths);
        startActivity(aIntent);
    }


}
