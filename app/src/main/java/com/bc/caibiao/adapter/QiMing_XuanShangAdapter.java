package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomeXuanShangTestModel;
import com.bc.caibiao.utils.ImageLoad;
import com.facebook.drawee.view.SimpleDraweeView;

public class QiMing_XuanShangAdapter extends AppBaseAdapter<HomeXuanShangTestModel> {

    public QiMing_XuanShangAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, R.layout.item_qiming_fragment_xuanshang, position);
        HomeXuanShangTestModel model=getItem(position);
        ((TextView) holder.getView(R.id.tvTitle)).setText(model.title);
        switch (model.labelType){
            case 0:
                ((TextView) holder.getView(R.id.tvLabel)).setText("公司起名");
                break;
            case 1:
                ((TextView) holder.getView(R.id.tvLabel)).setText("商标起名");
                break;
        }
        SimpleDraweeView pic=  (SimpleDraweeView)(holder.getView(R.id.ivLogo));
        ImageLoad.loadRes(pic,R.drawable.pic_morentouxiang);
        ((TextView) holder.getView(R.id.tvEndTime)).setText(model.endTime);
        ((TextView) holder.getView(R.id.tvMoney)).setText(model.money);
        return holder.getConvertView();
    }
}
