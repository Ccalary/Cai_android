package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.holder.QiMing_DaShiViewHolder;

/**
 * Created by Administrator on 2017/3/23.
 */

public class DashiTaskAdapter extends RecyclerView.Adapter<RewardTaskAdapter.TaskViewHolder>{
    private Context mContext;

    public DashiTaskAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RewardTaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task_reward, parent, false);
        RewardTaskAdapter rewardTaskAdapter = new RewardTaskAdapter(mContext);
        RewardTaskAdapter.TaskViewHolder dashiViewHolder = rewardTaskAdapter. new TaskViewHolder(view);
        return dashiViewHolder;
    }

    @Override
    public void onBindViewHolder(RewardTaskAdapter.TaskViewHolder holder, int position) {

        holder.mTvTaskTitle.setText("请大师给取个好名字");
        holder.mTvLabel.setText("商标取名");
        holder.mTvEndTime.setText("起名完成");
        holder.mTvPrice.setText("￥998");
        holder.mTvNumber.setText("章国兴大师");

    }

    @Override
    public int getItemCount() {
        return 2;
    }



}
