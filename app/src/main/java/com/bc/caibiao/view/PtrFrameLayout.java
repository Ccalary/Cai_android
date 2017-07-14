package com.bc.caibiao.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by chengyanfang on 2017/4/17.
 */

public class PtrFrameLayout {
    public interface PtrRefreshListener {
        public void onRefresh();
    }

    private Context mContext;
    private int mOrientation;
    private View mView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private RecyclerView mRecyclerView;
    private PtrRefreshListener mPtrRefreshListener;
    private View.OnClickListener mOnClickListener;
    private List<Object> list;
    public CheckIsCanPullListener mCheckIsCanPullListener;

    public PtrFrameLayout(Context context) {
        this(context, LinearLayoutManager.VERTICAL);
    }

    public PtrFrameLayout(Context context, int orientation) {
        mContext = context;
        mOrientation = orientation;
        init();
    }
    public void addData(List<Object> objects){
        list=objects;
    }

    private void init() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.bbs_ptr_layout, null);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) mView.findViewById(R.id.newptrlayout);
        mPtrClassicFrameLayout.disableWhenHorizontalMove(true);
        mPtrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(
                    in.srain.cube.views.ptr.PtrFrameLayout frame, View content,
                    View header) {
                if (mCheckIsCanPullListener != null) {
                    if (mCheckIsCanPullListener.isInterrupting()) {
                        return false;
                    }
                }
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(
                    in.srain.cube.views.ptr.PtrFrameLayout frame) {
                if (mPtrRefreshListener != null)
                    mPtrRefreshListener.onRefresh();

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(mOrientation);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.ptr_recyclerview);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0 && mOnClickListener != null)
                    mOnClickListener.onClick(null);
                return false;
            }
        });

    }

    public View getView() {
        return mView;
    }

    public void setOnClickListener(View.OnClickListener pClickListener) {
        mOnClickListener = pClickListener;
    }

    public void setPtrRefreshListener(PtrRefreshListener pListener) {
        mPtrRefreshListener = pListener;
    }

    public void stopPtrRefresh() {
        mPtrClassicFrameLayout.refreshComplete();

    }

    public <T> void setAdapter(BaseRecyclerAdapter<T> adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void autoRefresh() {

        if (this.mPtrClassicFrameLayout != null) {
            this.mPtrClassicFrameLayout.autoRefresh(true);
        }
    }

    public interface CheckIsCanPullListener {
        public boolean isInterrupting();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

}
