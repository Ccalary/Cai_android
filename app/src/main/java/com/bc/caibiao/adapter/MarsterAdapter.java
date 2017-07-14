package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.qiming.MarsterActivity;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MarsterAdapter extends RecyclerView.Adapter <MarsterAdapter.MarsterViewHolder>{
    private Context mContext;
    public MarsterAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MarsterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_marster_list, parent, false);
        MarsterViewHolder marsterViewHolder = new MarsterViewHolder(view);

        return marsterViewHolder;
    }

    @Override
    public void onBindViewHolder(MarsterViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class MarsterViewHolder extends RecyclerView.ViewHolder {

        ImageView mIv;
        private final TextView mTv;

        public MarsterViewHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    //定义一个接口作为监听者
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
