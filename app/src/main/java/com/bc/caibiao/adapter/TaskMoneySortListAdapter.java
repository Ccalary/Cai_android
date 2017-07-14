package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.caibiao.R;

/**
 * 任务广场金额列表
 */

public class TaskMoneySortListAdapter extends AppBaseAdapter<String> {
    public TaskMoneySortListAdapter(Context mContext) {
        super(mContext);
    }

    private Integer checkPosition;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, R.layout.item_task_money_sort, position);
        ((TextView) holder.getView(R.id.tvMoney)).setText(getItem(position));
        ((ImageView) holder.getView(R.id.ivCheck)).setVisibility(checkPosition != null && checkPosition == position ? View.VISIBLE : View.GONE);
        return holder.getConvertView();
    }
}
