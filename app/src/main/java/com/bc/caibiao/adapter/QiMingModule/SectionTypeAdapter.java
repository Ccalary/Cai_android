package com.bc.caibiao.adapter.QiMingModule;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.TaskSectionType;
import com.bc.caibiao.utils.AppUtil;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class SectionTypeAdapter extends BaseAdapter<TaskSectionType> {

    int index = -1;

    public SectionTypeAdapter(Context context, ArrayList<TaskSectionType> mDatas) {
        super(context, mDatas);
    }

    public void setIndex(int aindex) {
        this.index = aindex;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fl_forum_account_list_item;
    }

    @Override
    protected void setViewData(View convertView, Holder holder, int position) {
        // TODO Auto-generated method stub
        TaskSectionType mAccountRecord = mDatas.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.userName.setText(mAccountRecord.title);

        viewHolder.mTopDevider.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lps = (RelativeLayout.LayoutParams) viewHolder.mBottomDevider.getLayoutParams();
        if (position == mDatas.size() - 1) {
            lps.setMargins(0, 0, 0, 0);
        } else {
            lps.setMargins(AppUtil.dip2px(mContext, 10), 0, 0, 0);
        }
        viewHolder.mTopDevider.setLayoutParams(lps);

        if (index == position) {
            viewHolder.mSelectedImg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mSelectedImg.setVisibility(View.GONE);
        }

    }

    @Override
    protected Holder getViewHolder(View convertView) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.userName = (TextView) convertView.findViewById(R.id.fl_forum_account_list_item_tv);
        viewHolder.mTopDevider = convertView.findViewById(R.id.iv_top_devider);
        viewHolder.mBottomDevider = convertView.findViewById(R.id.iv_bottom_devider);
        viewHolder.mSelectedImg = (ImageView) convertView.findViewById(R.id.iv_selected);
        return viewHolder;
    }

    class ViewHolder implements Holder {
        TextView userName;
        View mTopDevider;
        View mBottomDevider;
        ImageView mSelectedImg;
    }

}
