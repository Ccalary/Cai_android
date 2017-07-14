package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bc.caibiao.R;
import com.bc.caibiao.utils.ImageLoad;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 任务详情中所有投稿人适配器
 */

public class TaskContributorListAdapter extends AppBaseAdapter<String> {
    public TaskContributorListAdapter(Context mContext) {
        super(mContext);
    }

    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private int currentType;

    @Override
    public int getItemViewType(int position) {

        if (position != 1) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentType = getItemViewType(position);
        ViewHolder holder1 = null;
        ViewHolder holder2 = null;
        SimpleDraweeView simpleDraweeView;
        if (currentType == TYPE_1) {
            holder1 = ViewHolder.get(getContext(), convertView, parent, R.layout.item_task_detail_contributor, position);
            simpleDraweeView=holder1.getView(R.id.sdvPortrait);
            ImageLoad.loadRes(simpleDraweeView,R.drawable.pic_man);
        } else {
            holder2 = ViewHolder.get(getContext(), convertView, parent, R.layout.item_task_detail_contributor_self, position);
            simpleDraweeView=holder2.getView(R.id.sdvPortrait);
            ImageLoad.loadRes(simpleDraweeView,R.drawable.pic_women);
        }

        return currentType == TYPE_1 ? holder1.getConvertView() : holder2.getConvertView();
    }
}
