package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.TeacherModel;
import com.bc.caibiao.utils.AppUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/26.
 */

public class CoverFlowAdapter extends BaseAdapter {

    private ArrayList<TeacherModel> mData = new ArrayList<>(0);
    private Context mContext;

    public CoverFlowAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<TeacherModel> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            rowView.setLayoutParams(new Gallery.LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext,170), Gallery.LayoutParams.MATCH_PARENT));
            rowView.setScaleX(0.7f);
            rowView.setScaleY(0.7f);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.img1);

            viewHolder.mName = (TextView) rowView
                    .findViewById(R.id.name1);

            viewHolder.mDesc = (TextView) rowView
                    .findViewById(R.id.desc1);

            viewHolder.mDealCnt = (TextView) rowView
                    .findViewById(R.id.sign_count1);

            viewHolder.image.setLayoutParams(new RelativeLayout.LayoutParams(AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext,170),AppUtil.getWidth(mContext) - AppUtil.dip2px(mContext,180)));
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        Glide.with(mContext).load(mData.get(position).portrait).into(holder.image);
        holder.mName.setText(mData.get(position).memberName);
        holder.mDesc.setText(mData.get(position).memberExtend.introduce);
        holder.mDealCnt.setText("成功起名"+mData.get(position).memberExtend.daShiTaskNum2+"例");

        return rowView;
    }


    static class ViewHolder {
        public ImageView image;
        public TextView mName;
        public TextView mDesc;
        public TextView mDealCnt;
    }
}
