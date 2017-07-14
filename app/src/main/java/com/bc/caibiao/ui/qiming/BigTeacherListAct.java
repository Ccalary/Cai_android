package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.TeacherCardAdapter;
import com.bc.caibiao.model.HomePageModel.TeacherModel;
import com.bc.caibiao.model.MarkModel.BigTeacherList;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ProgressSubscribe;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.PtrFrameLayout;
import com.bc.caibiao.view.TopBarLayout;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class BigTeacherListAct extends BaseActivity {

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.id_tablayout)
    TopBarLayout mTopLayout;

    PtrFrameLayout mPtrFrameLayout;
    TeacherCardAdapter mAdapter;

    int currentPage = 1;

    private ArrayList<TeacherModel> mTeacherList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigtteacher_layotu);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTopLayout.showLeft();
        mTopLayout.setTitle("大师列表");
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());

        mPtrFrameLayout.getRecyclerView().setLayoutManager(new GridLayoutManager(this,2));

        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                mPtrFrameLayout.stopPtrRefresh();
            }
        });
    }

    private void initData(){
        requestData();
    }

    private void requestData(){
        BCHttpRequest.getQiMingInterface().getDaShiListApi("10",currentPage+"")
                .compose(HttpResponseHelper.<BigTeacherList>getAllData())
                .subscribe(new ProgressSubscribe<BigTeacherList>(mContext) {
                    @Override
                    protected void _onNext(BigTeacherList homePageModel) {

                        mPtrFrameLayout.stopPtrRefresh();

                        if(currentPage == 1){
                            mTeacherList.clear();
                        }

                        mTeacherList.addAll(homePageModel.data);

                        loadAdapter();

                        if(homePageModel.data.size() < 10){
                            mAdapter.canLoadMore(false);
                        }else{
                            mAdapter.canLoadMore(true);
                        }
                    }
                });
    }


    private void loadAdapter(){
        if (mAdapter == null) {
            mAdapter = new TeacherCardAdapter(this, mTeacherList);
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.canLoadMore(false);
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent aIntent = new Intent(mContext,MarsterDetailActivity.class);
                    aIntent.putExtra("memberId",mTeacherList.get(arg2).memberId);
                    mContext.startActivity(aIntent);
                }
            });
        } else {
            mAdapter.setData(mTeacherList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
