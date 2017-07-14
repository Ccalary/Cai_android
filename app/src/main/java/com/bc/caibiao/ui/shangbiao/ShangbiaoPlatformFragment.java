package com.bc.caibiao.ui.shangbiao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.ShangbiaoResultAdapter;
import com.bc.caibiao.adapter.ShangbiaoSuggestionAdapter;
import com.bc.caibiao.model.MarkModel.MarkModelData;
import com.bc.caibiao.model.MarkModel.MarkRecommandProduct;
import com.bc.caibiao.model.MarkModel.MarkSearchResultList;
import com.bc.caibiao.model.ShangbiaoSuggestModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BaseFragment;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Observer;

/**
 * 商标列表页面
 *
 * @author chengyanfang
 */
@SuppressLint("ValidFragment")
public class ShangbiaoPlatformFragment extends BaseFragment {

    @Bind(R.id.gvProduct)
    GridView gvProduct;

    @Bind(R.id.ptrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    //类型
    int mType = 0;

    //当前页数
    int currentPage = 1;
    boolean isLoadmore = false;
    boolean isCanLoadMore = false;

    ArrayList<MarkRecommandProduct> mMarkRecommandProducts = new ArrayList<>();
    ShangbiaoSuggestionAdapter mAdapter;

    @SuppressLint("ValidFragment")
    public ShangbiaoPlatformFragment(int type){
        mType = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.fragment_shangbiao_platform, container, false);
            ButterKnife.bind(this,mView);
        }
        isCreateView = true;
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }


    protected void initView() {
        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        mPtrFrameLayout.setPullToRefresh(false);
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                loadData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        this.gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent=new Intent(getActivity(),ShangpingDetailActivity.class);
                detailIntent.putExtra("productId",mMarkRecommandProducts.get(position).product_id);
                startActivity(detailIntent);
            }
        });
    }


    @Override
    public void OnClick(View v) {

    }

    protected void initData() {
        loadData();
    }

    private void loadData(){
        BCHttpRequest.getMarkInterface().markCategoryedProductListApi(currentPage,10,mType)
                .compose(HttpResponseHelper.<MarkModelData>getAllData())
                .subscribe(new Observer<MarkModelData>() {
                    @Override
                    public void onCompleted() {
                        ((BaseActivity)getActivity()).dismissProgressHUD();
                        isLoadmore = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity)getActivity()).dismissProgressHUD();
                        ToastUtils.showShort(getActivity(),"网络错误");
                        isLoadmore = false;
                    }

                    @Override
                    public void onNext(MarkModelData markModelData) {

                        ((BaseActivity)getActivity()).dismissProgressHUD();

                        if(currentPage == 1){
                            mMarkRecommandProducts.clear();
                            mPtrFrameLayout.refreshComplete();
                        }

                        if(markModelData.data.size() == 10){
                            isCanLoadMore = true;
                        }else{
                            isCanLoadMore = false;
                        }

                        mMarkRecommandProducts.addAll(markModelData.data);

                        setAdapter();

                        isLoadmore = false;
                    }
                });
    }


    private void setAdapter(){
        if(mAdapter == null){
            mAdapter = new ShangbiaoSuggestionAdapter(getActivity(),true);
            mAdapter.addList(mMarkRecommandProducts);
            gvProduct.setAdapter(mAdapter);

            mAdapter.setLoadmoreInterface(new ShangbiaoResultAdapter.LoadmoreInterface() {
                @Override
                public void loadmore() {
                    if(!isCanLoadMore){
                        ToastUtils.showShort(getActivity(),"已全部加载完毕");
                        return;
                    }
                    if(!isLoadmore){
                        isLoadmore = true;
                        currentPage = currentPage + 1;

                        ((BaseActivity)getActivity()).showProgressHUD(getActivity(),"加载中...");

                        loadData();
                    }
                }
            });
        }else{
            mAdapter.clearList();
            mAdapter.addList(mMarkRecommandProducts);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
