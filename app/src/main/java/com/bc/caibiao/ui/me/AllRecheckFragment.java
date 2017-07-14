package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.ReCheckTaskAdapter;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.BrandRecheck;
import com.bc.caibiao.model.BrandRecheckModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.view.PtrFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observer;

/**
 * Created by Administrator on 2017/4/19.
 * 复查全部
 */
public class AllRecheckFragment extends Fragment {
    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    private int currentPage = 1;
    ArrayList<BrandRecheck> mBrandRecheckList = new ArrayList<>();
    private ReCheckTaskAdapter mRecheckTaskAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recheck_restult, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mContainerLayout = (RelativeLayout) view.findViewById(R.id.container_relativelayout);
        mPtrFrameLayout = new PtrFrameLayout(getActivity());
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                requestAllRecheckData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPage = 1;
        requestAllRecheckData();
    }

    /**
     * 全部复查请求
     */
    private void requestAllRecheckData() {
        BCHttpRequest.getOtherInterface().getAllListBrandRecheckApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)), currentPage, Constant.PAGESIZE)
                .compose(HttpResponseHelper.<BrandRecheckModel>getAllData())
                .subscribe(new Observer<BrandRecheckModel>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onNext(BrandRecheckModel brandRecheckModel) {
                        mPtrFrameLayout.stopPtrRefresh();
                        if (currentPage == 1) {
                            mBrandRecheckList.clear();
                        }
                        mBrandRecheckList.addAll(brandRecheckModel.data);

                        setAdapter();

                        if (brandRecheckModel.data.size() < Constant.PAGESIZE) {
                            mRecheckTaskAdapter.canLoadMore(false);
                        } else {
                            mRecheckTaskAdapter.canLoadMore(true);
                        }
                    }
                });
    }

    /**
     * 加载数据
     */
    private void setAdapter() {
       // if (mRecheckTaskAdapter == null) {
            mRecheckTaskAdapter = new ReCheckTaskAdapter(getActivity(), mBrandRecheckList);
            mPtrFrameLayout.setAdapter(mRecheckTaskAdapter);
            mRecheckTaskAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {
                @Override
                public void OnLoadMore() {
                    currentPage++;
                    requestAllRecheckData();
                }
            });
            mRecheckTaskAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent intent = new Intent(getActivity(), RecheckDetailActivity.class);
                    intent.putExtra("recheckId", mBrandRecheckList.get(arg2).getRecheckId());
                    startActivity(intent);
                }
            });
       // } else {
        //    mRecheckTaskAdapter.setData(mBrandRecheckList);
        //    mRecheckTaskAdapter.notifyDataSetChanged();
       // }
    }
}
