package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.DictionaryItem;

/**
 * 任务要求标签
 */

public class TaskDemandGridAdapter extends AppBaseAdapter<DictionaryItem> {

    public TaskDemandGridAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, R.layout.item_task_demand, position);
        ((TextView) holder.getView(R.id.tvLabel)).setText(getItem(position).getItemContent());
        return holder.getConvertView();
    }
}
