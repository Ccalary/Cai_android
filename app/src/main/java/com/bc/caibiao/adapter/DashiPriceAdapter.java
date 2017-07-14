package com.bc.caibiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.DashiPriceModel;
import com.bc.caibiao.model.HomeXuanShangTestModel;
import com.bc.caibiao.utils.ImageLoad;
import com.bc.caibiao.utils.ScreenUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class DashiPriceAdapter extends AppBaseAdapter<DashiPriceModel> {
    private int picW;
    private Context mContext;

    public DashiPriceAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        picW = (ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 46 + 10 * 3)) / 4;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(getContext(), convertView, parent, R.layout.item_dashi_price, position);
        DashiPriceModel model = getItem(position);
        ((TextView) holder.getView(R.id.tvPrice)).setText(model.price);
        ((CheckBox) holder.getView(R.id.cb_xuanze)).setChecked(model.isCheck);
        LinearLayout llPics = holder.getView(R.id.ll_pics);
        llPics.removeAllViews();
        addView(model, llPics);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(DashiPriceModel dashiPriceModel:list)
                    dashiPriceModel.isCheck=false;
                getList().get(position).isCheck=true;
                notifyDataSetChanged();
            }
        });
        return holder.getConvertView();
    }

    private void addView(DashiPriceModel model, LinearLayout linearLayout) {
        for (int i = 0; i < model.picCount; i++) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(picW, picW);
            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoad.loadRes(simpleDraweeView, R.drawable.pic_morentouxiang);
            if (i != model.picCount - 1) {
                ll.rightMargin = ScreenUtils.dip2px(mContext, 10);
            }
            simpleDraweeView.setLayoutParams(ll);
            linearLayout.addView(simpleDraweeView);
        }
    }
}
