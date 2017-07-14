package com.bc.caibiao.ui.qiming;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.QiMingModule.CategoryAdapter;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.HomePageModel.CategotyChild;
import com.bc.caibiao.model.MarkModel.TaskCategory;
import com.bc.caibiao.model.MarkModel.TaskCategoryList;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;
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

public class CategoryListAct extends BaseActivity {

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    @Bind(R.id.id_tablayout)
    TopBarLayout mTopLayout;

    PtrFrameLayout mPtrFrameLayout;
    CategoryAdapter mAdapter;

    @Bind(R.id.etBrandName)
    EditText mBrandName;

    @Bind(R.id.tv_search)
    TextView tvSearch;

    @Bind(R.id.et_search)
    EditText etSearch;

    private ArrayList<TaskCategory> mTaskCategory = new ArrayList();
    private ArrayList<TaskCategory> mSearchCategory = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_act);
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

        mBrandName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 0){
                    mSearchCategory.clear();
                    mSearchCategory.addAll(mTaskCategory);
                    loadAdapter();
                }else{
                    mSearchCategory.clear();
                    for(TaskCategory taskCategory : mTaskCategory){
                        if(taskCategory.getCategory_name().contains(s)){
                            mSearchCategory.add(taskCategory);
                        }
                    }
                    loadAdapter();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,CategorySearchResult.class));
            }
        });
    }

    private void initData(){
        //请求分类
        //@Field("filter_level") String filterLevel,@Field("filter_parent_id") String filterParentId,@Field("pageSize") String pageSize,@Field("pageNo") String pageNo

//        BCHttpRequest.getQiMingInterface().getTaskCategoryListApi(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))
        BCHttpRequest.getQiMingInterface().getTaskCategoryListApi("1","","100","1")
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
                        mSearchCategory.addAll(taskPriceList.data);
                        loadAdapter();
                    }
                });
    }

    private void loadAdapter(){
        if (mAdapter == null) {
            mAdapter = new CategoryAdapter(this, mSearchCategory);
            mPtrFrameLayout.setAdapter(mAdapter);
            mAdapter.canLoadMore(false);
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    ArrayList<String> childStr = new ArrayList<String>();
                    TaskCategory taskCategory = mSearchCategory.get(arg2);
                    Intent aIntent = new Intent(CategoryListAct.this,CategoryChildAct.class);
                    aIntent.putStringArrayListExtra("child",childStr);
                    aIntent.putExtra("id",taskCategory.getId());
                    aIntent.putExtra("title",taskCategory.getCategory_title());
                    CategoryListAct.this.startActivity(aIntent);
                }
            });
        } else {
            mAdapter.setData(mSearchCategory);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
