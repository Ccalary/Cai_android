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


/**
 * Created by chengyanfang on 2017/4/26.
 */

public class NoRefreshlayout {

    private Context mContext;
    private int mOrientation;
    private View mView;
    private RecyclerView mRecyclerView;
    private View.OnClickListener mOnClickListener;
    private List<Object> list;

    public NoRefreshlayout(Context context) {
        this(context, LinearLayoutManager.VERTICAL);
    }

    public NoRefreshlayout(Context context, int orientation) {
        mContext = context;
        mOrientation = orientation;
        init();
    }
    public void addData(List<Object> objects){
        list=objects;
    }

    private void init() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.bbs_noptr_layout, null);
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


    public <T> void setAdapter(BaseRecyclerAdapter<T> adapter) {
        mRecyclerView.setAdapter(adapter);
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
