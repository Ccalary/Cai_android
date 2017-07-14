package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.CategoryChildAdapter;
import com.bc.caibiao.adapter.QiMingModule.CategoryChildDetailAdapter;
import com.bc.caibiao.model.MarkModel.TaskCategory;
import com.bc.caibiao.model.MarkModel.TaskCategoryList;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.PtrFrameLayout;
import com.bc.caibiao.view.TopBarLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class CategoryChildActDetail extends BaseActivity {

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.id_tablayout)
    TopBarLayout mTopLayout;

    @Bind(R.id.title)
    TextView tvTitle;

    PtrFrameLayout mPtrFrameLayout;
    CategoryChildDetailAdapter mAdapter;

    private ArrayList<TaskCategory> mTaskCategory = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_qiming_new);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTopLayout.showLeft();
        mTopLayout.setTitle("分类查询");
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                mPtrFrameLayout.stopPtrRefresh();
            }
        });

        String title=getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title)){
            ViewGroup.LayoutParams layoutParams=tvTitle.getLayoutParams();
            layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            tvTitle.setLayoutParams(layoutParams);
            tvTitle.setText(title);
        }
    }

    private void initData(){
//        mTaskCategory = getIntent().getStringArrayListExtra("child");
//        if(mTaskCategory != null){
//            if(mTaskCategory.isEmpty()){
//                mTaskCategory.add("无数据");
//            }
//        }
//        loadAdapter();


        final String id=getIntent().getStringExtra("id");
        //请求分类
        BCHttpRequest.getQiMingInterface().getTaskCategoryListApi("",id,"100","1")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskCategoryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TaskCategoryList taskPriceList) {
                        mTaskCategory.addAll(taskPriceList.data);
                        loadAdapter();
                    }
                });
    }

    private void loadAdapter(){
        if (mAdapter == null) {
            mAdapter = new CategoryChildDetailAdapter(this, mTaskCategory);
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.canLoadMore(false);

//            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                    TaskCategory taskCategory = mTaskCategory.get(arg2);
//                    Intent aIntent = new Intent(CategoryChildActDetail.this,CategoryChildActDetail.class);
//                    aIntent.putExtra("id",taskCategory.getId());
//                    aIntent.putExtra("title",taskCategory.getCategory_title());
//                    CategoryChildActDetail.this.startActivity(aIntent);
//                }
//            });
        } else {
            mAdapter.setData(mTaskCategory);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
