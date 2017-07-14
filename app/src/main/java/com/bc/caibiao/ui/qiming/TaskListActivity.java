package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.IndustryList;
import com.bc.caibiao.adapter.TaskListAdapter;
import com.bc.caibiao.model.HomePageModel.PlaygroundTask;
import com.bc.caibiao.model.HomePageModel.PlaygroundTaskList;
import com.bc.caibiao.model.HomePageModel.TaskParentModel;
import com.bc.caibiao.model.HomePageModel.TaskSectionType;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ProgressSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.utils.MarkModuleUtil;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.PtrFrameLayout;
import com.bc.caibiao.view.popupwindow.TaskSectionPop;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskListActivity extends BaseActivity{

    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.tv_selector1)
    TextView mSelectorTv1;

    @Bind(R.id.tv_selector2)
    TextView mSelectorTv2;

    @Bind(R.id.iv_arrow1)
    ImageView mArrow1;

    @Bind(R.id.iv_arrow2)
    ImageView mArrow2;

    @Bind(R.id.llv_selector_layout)
    LinearLayout mSelectorLayout;

    @Bind(R.id.selector1)
    RelativeLayout mSelector1;

    @Bind(R.id.selector2)
    RelativeLayout mSelector2;

    @Bind(R.id.empty_img)
    ImageView mEmptyImg;

    ArrayList<TaskSectionType> mTaskSectionTypeList1 = new ArrayList<>();
    TaskSectionPop mSelectorPopupWindow1;

    ArrayList<TaskSectionType> mTaskSectionTypeList2 = new ArrayList<>();;
    TaskSectionPop mSelectorPopupWindow2;

    TaskListAdapter mAdapter;

    String namedCategory = "";
    String orderBy = "";
    int currentPage = 1;
    int index1 = -1, index2 = -1;
    ArrayList<PlaygroundTask> mDatas = new ArrayList<PlaygroundTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_playgrount);
        ButterKnife.bind(this);

        initUI();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        loadData();
    }

    protected void initUI() {
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

        mSelector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector1();
            }
        });

        mSelector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector2();
            }
        });

        findViewById(R.id.rlv_push_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //是否登录
                if(!MarkModuleUtil.isLogin()){
                    Intent aIntent = new Intent(TaskListActivity.this, LoginActivity.class);
                    startActivity(aIntent);
                    return;
                }


                TaskListActivity.this.startActivity(new Intent(TaskListActivity.this,PublishTaskActivity.class));
            }
        });
    }

    protected void initData() {
        TaskSectionType mTaskSectionType = new TaskSectionType();
        mTaskSectionType.title = "分类";
        mTaskSectionType.id = "0";
        mTaskSectionTypeList1.add(mTaskSectionType);

        TaskSectionType mTaskSectionType2 = new TaskSectionType();
        mTaskSectionType2.title = "商标起名";
        mTaskSectionType2.id = "1";
        mTaskSectionTypeList1.add(mTaskSectionType2);

        TaskSectionType mTaskSectionType3 = new TaskSectionType();
        mTaskSectionType3.title = "公司起名";
        mTaskSectionType3.id = "2";
        mTaskSectionTypeList1.add(mTaskSectionType3);


        TaskSectionType mTaskSectionType21 = new TaskSectionType();
        mTaskSectionType21.title = "排序";
        mTaskSectionType21.id = "2";
        mTaskSectionTypeList2.add(mTaskSectionType21);

        TaskSectionType mTaskSectionType22 = new TaskSectionType();
        mTaskSectionType22.title = "金额从低到高";
        mTaskSectionType22.id = "3";
        mTaskSectionTypeList2.add(mTaskSectionType22);

        TaskSectionType mTaskSectionType23 = new TaskSectionType();
        mTaskSectionType23.title = "金额从高到低";
        mTaskSectionType23.id = "4";
        mTaskSectionTypeList2.add(mTaskSectionType23);

        TaskSectionType mTaskSectionType24 = new TaskSectionType();
        mTaskSectionType24.title = "按发布时间最早";
        mTaskSectionType24.id = "2";
        mTaskSectionTypeList2.add(mTaskSectionType24);

        TaskSectionType mTaskSectionType25 = new TaskSectionType();
        mTaskSectionType25.title = "按发布时间最晚";
        mTaskSectionType25.id = "1";
        mTaskSectionTypeList2.add(mTaskSectionType25);

        TaskSectionType mTaskSectionType26 = new TaskSectionType();
        mTaskSectionType26.title = "投稿人最少";
        mTaskSectionType26.id = "5";
        mTaskSectionTypeList2.add(mTaskSectionType26);

        TaskSectionType mTaskSectionType27 = new TaskSectionType();
        mTaskSectionType27.title = "投稿人最多";
        mTaskSectionType27.id = "6";
        mTaskSectionTypeList2.add(mTaskSectionType27);

        namedCategory = "0";
        orderBy = "2";

        loadData();
    }

    private void loadData(){

        if("0".equals(namedCategory)){
            BCHttpRequest
                    .getQiMingInterface()
                    .getTaskPlayListApi(orderBy,String.valueOf(10),String.valueOf(currentPage))
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TaskParentModel>() {
                        @Override
                        public void onCompleted() {
                            mPtrFrameLayout.stopPtrRefresh();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mPtrFrameLayout.stopPtrRefresh();
                        }

                        @Override
                        public void onNext(TaskParentModel aTaskParentModel) {

                            mPtrFrameLayout.stopPtrRefresh();

                            if(!"0".equals(aTaskParentModel.state) && aTaskParentModel.fieldErrors.size()>0){
                                ToastUtils.showShort(TaskListActivity.this,aTaskParentModel.fieldErrors.get(0).getMessage());
                                return;
                            }

                            if(currentPage == 1){
                                mDatas.clear();
                            }

                            mDatas.addAll(aTaskParentModel.data.data);

                            if(mDatas.isEmpty()){
                                mEmptyImg.setVisibility(View.VISIBLE);
                            }else{
                                mEmptyImg.setVisibility(View.GONE);
                            }

                            loadAdapter();

                            if(aTaskParentModel.data.data.size() < 10){
                                mAdapter.canLoadMore(false);
                            }else{
                                mAdapter.canLoadMore(true);
                            }

                        }
                    });
        }else{
            BCHttpRequest
                    .getQiMingInterface()
                    .getTaskPlayListApiWithCategory(orderBy,String.valueOf(10),String.valueOf(currentPage),"0".equals(namedCategory)?"0":namedCategory)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TaskParentModel>() {
                        @Override
                        public void onCompleted() {
                            mPtrFrameLayout.stopPtrRefresh();
                            if(mDatas.isEmpty()){
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
                        public void onNext(TaskParentModel aTaskParentModel) {

                            mPtrFrameLayout.stopPtrRefresh();

                            if(!"0".equals(aTaskParentModel.state) && aTaskParentModel.fieldErrors.size()>0){
                                ToastUtils.showShort(TaskListActivity.this,aTaskParentModel.fieldErrors.get(0).getMessage());
                                return;
                            }

                            if(currentPage == 1){
                                mDatas.clear();
                            }

                            mDatas.addAll(aTaskParentModel.data.data);

                            if(mDatas.isEmpty()){
                                mEmptyImg.setVisibility(View.VISIBLE);
                            }else{
                                mEmptyImg.setVisibility(View.GONE);
                            }

                            loadAdapter();

                            if(aTaskParentModel.data.data.size() < 10){
                                mAdapter.canLoadMore(false);
                            }else{
                                mAdapter.canLoadMore(true);
                            }

                        }
                    });
        }
    }


    private void loadAdapter(){
        if (mAdapter == null) {
            mAdapter = new TaskListAdapter(this, mDatas);
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
                    Intent aIntent = new Intent(TaskListActivity.this,TaskDetailActivity.class);
                    aIntent.putExtra("taskID",mDatas.get(arg2).taskId);
                    startActivity(aIntent);

                }
            });
        } else {
            mAdapter.setData(mDatas);
            mAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 选择分类
     * */
    private void showSelector1() {

        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        mArrow1.startAnimation(rotateAnimation);

        if (mSelectorPopupWindow1 == null) {
            mSelectorPopupWindow1 = new TaskSectionPop(this, mSelectorLayout, mTaskSectionTypeList1, new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    RotateAnimation rotateAnimation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(500);
                    rotateAnimation.setFillAfter(true);
                    mArrow1.startAnimation(rotateAnimation);
                    mSelector1.setBackgroundColor(Color.WHITE);
                }
            }, new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSelectorTv1.setText(mTaskSectionTypeList1.get(position).title);
                    namedCategory = mTaskSectionTypeList1.get(position).id;
                    currentPage = 1;
                    loadData();
                    index1 = position;
                    mSelectorPopupWindow1.dismissPopupWindow();
                }
            });
            mSelectorPopupWindow1.setTag(1001);
            mSelectorPopupWindow1.setIndex(index1);
        } else {
            mSelectorPopupWindow1.showPopupWindow();
            mSelectorPopupWindow1.setIndex(index1);
        }
    }


    /**
     * 选择排序
     * */
    private void showSelector2() {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        mArrow2.startAnimation(rotateAnimation);

        if (mSelectorPopupWindow2 == null) {
            mSelectorPopupWindow2 = new TaskSectionPop(this, mSelectorLayout, mTaskSectionTypeList2, new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    RotateAnimation rotateAnimation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(500);
                    rotateAnimation.setFillAfter(true);
                    mArrow2.startAnimation(rotateAnimation);
                    mSelector2.setBackgroundColor(Color.WHITE);
                }
            }, new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSelectorTv2.setText(mTaskSectionTypeList2.get(position).title);
                    orderBy = mTaskSectionTypeList2.get(position).id;
                    currentPage = 1;
                    loadData();
                    index2 = position;
                    mSelectorPopupWindow2.dismissPopupWindow();
                }
            });
            mSelectorPopupWindow2.setTag(1002);
            mSelectorPopupWindow2.setIndex(index2);
        } else {
            mSelectorPopupWindow2.showPopupWindow();
            mSelectorPopupWindow2.setIndex(index2);
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
