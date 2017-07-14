package com.bc.caibiao.adapter.QiMingModule;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.HomePageModel.CaseModel;
import com.bc.caibiao.utils.AppUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/19.
 */

public class MasterExampleAdapter extends BaseAdapter<CaseModel> {

    int width = 0;

    public MasterExampleAdapter(Activity context, ArrayList<CaseModel> objects) {
        super(context, objects);
        width = (AppUtil.getWidth(mContext)-AppUtil.dip2px(mContext,34))/3;
    }

    @Override
    protected int getResourceId() {
        return R.layout.dashi_example_item;
    }

    @Override
    protected void setViewData(View convertView, Holder holder, int position) {
        final ViewHolder aHolder = (ViewHolder) holder;
        Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+mDatas.get(position).picPath).placeholder(R.drawable.pic_01).into(aHolder.mUserCenterImg);

        aHolder.mUserCenterImg.setLayoutParams(new RelativeLayout.LayoutParams(width,width));
    }

    @Override
    protected Holder getViewHolder(View convertView) {
        ViewHolder aHolder = new ViewHolder();
        aHolder.mUserCenterImg = (ImageView) convertView.findViewById(R.id.img);
        return aHolder;
    }

    static class ViewHolder implements Holder {
        ImageView mUserCenterImg;
    }
}
