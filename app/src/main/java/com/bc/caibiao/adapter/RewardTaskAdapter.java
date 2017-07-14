package com.bc.caibiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.ui.me.DashiOfMeTaskDetailActivity;
import com.bc.caibiao.ui.me.DashiTaskDetailActivity;
import com.bc.caibiao.ui.me.ImMasterActivity;
import com.bc.caibiao.ui.me.MyMasterTaskActivity;
import com.bc.caibiao.ui.qiming.TaskDetailActivity;

/**
 * Created by Administrator on 2017/3/21.
 */

public class RewardTaskAdapter extends RecyclerView.Adapter<RewardTaskAdapter.TaskViewHolder>{
    private Context mContext;

    public RewardTaskAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task_reward, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.mTvTaskTitle.setText("帮忙取个原创的商标名");
        holder.mTvLabel.setText("公司取名");
        holder.mTvEndTime.setText("七天后截稿");
        holder.mTvPrice.setText("￥25");
        holder.mTvNumber.setText("2个投稿");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

     class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTaskTitle, mTvLabel,mTvEndTime,mTvPrice,mTvNumber;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mTvTaskTitle = (TextView) itemView.findViewById(R.id.tv1);
            mTvLabel = (TextView) itemView.findViewById(R.id.tv_qiming);
            mTvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            mTvEndTime = (TextView) itemView.findViewById(R.id.tv7);
            mTvPrice = (TextView) itemView.findViewById(R.id.tv2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if(mContext instanceof MyMasterTaskActivity){
                        intent=new Intent(mContext, DashiTaskDetailActivity.class);
                    }
                    else if(mContext instanceof ImMasterActivity){
                        intent=new Intent(mContext, DashiOfMeTaskDetailActivity.class);
                    }
                    else{
                     intent=new Intent(mContext, TaskDetailActivity.class);
                    intent.putExtra("isFromMe",true);}
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
