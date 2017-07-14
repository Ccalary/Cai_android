package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.OrderStateHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收藏的商标Adapter
 */

public class HistroiesAdapter extends RecyclerView.Adapter<HistroiesAdapter.TaskViewHolder> {
    private Context mContext;
    private boolean isOpenHistory = false;
    private ArrayList<OrderStateHistory> mHistoryList = new ArrayList<>();

    public HistroiesAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_history, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.mTvHistoryStatus.setText("[" + mHistoryList.get(position).getStatus() + "]");
        holder.mTvHistoryTime.setText(mHistoryList.get(position).getDate_added());
        if (mHistoryList.size() == 1) {
            holder.mHistoryIcon.setVisibility(View.GONE);
        } else {
            if ((mHistoryList.size() - 1) == position) {
                holder.mHistoryIcon.setVisibility(View.VISIBLE);
                holder.mIamgeDown.setVisibility(View.GONE);
                holder.mImageUp.setVisibility(View.VISIBLE);
            } else {
                if (isOpenHistory) {
                    holder.mHistoryIcon.setVisibility(View.GONE);
                } else {
                    holder.mHistoryIcon.setVisibility(View.VISIBLE);
                    holder.mIamgeDown.setVisibility(View.VISIBLE);
                    holder.mImageUp.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mHistoryList != null) {
            if (isOpenHistory) {
                return mHistoryList.size();
            } else {
                return 1;
            }
        }
        return 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTvHistoryStatus, mTvHistoryTime;
        LinearLayout mHistoryIcon;
        ImageView mIamgeDown, mImageUp;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mTvHistoryStatus = (TextView) itemView.findViewById(R.id.tv_history_status);
            mTvHistoryTime = (TextView) itemView.findViewById(R.id.tv_history_time);
            mHistoryIcon = (LinearLayout) itemView.findViewById(R.id.history_icon);
            mIamgeDown = (ImageView) itemView.findViewById(R.id.icon_down);
            mImageUp = (ImageView) itemView.findViewById(R.id.icon_up);
            mHistoryIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isOpenHistory) {
                        isOpenHistory = false;
                    } else {
                        isOpenHistory = true;
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 添加list集合
     */
    public void addList(List<OrderStateHistory> arrayList) {
        mHistoryList.addAll(arrayList);
    }

    /**
     * 清空list集合
     */
    public void clearList() {
        if (mHistoryList != null && mHistoryList.size() > 0) {
            mHistoryList.clear();
        }
    }
}
