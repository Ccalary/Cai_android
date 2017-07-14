package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.MarkModel.Advertise;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.AppUtil;
import com.bc.caibiao.utils.Config;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class HomeShangBiaoAdapter extends BaseRecyclerAdapter<Advertise> {


    int height = 0;


    public HomeShangBiaoAdapter(Context context, ArrayList<Advertise> datas) {
        super(context, datas);

        height = AppUtil.getWidth(context)*284/750;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_shangbiao_home_adapter;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final Advertise aRewardTaskModel = mDatas.get(position);

        Glide.with(mContext).load(URLConfig.baseUrl_pic_oss+aRewardTaskModel.adImg).placeholder(R.drawable.pic_wait).into(aItemViewHolder.mHeaderImg);

        aItemViewHolder.mHeaderImg.setLayoutParams(new RelativeLayout.LayoutParams(AppUtil.getWidth(mContext),height));

        if (mItemClickListener != null) {
            aItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, null, position, 0);
                }
            });
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;

        public ImageView mHeaderImg;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mHeaderImg = (ImageView) arg0.findViewById(R.id.ivLogo);
        }

    }
}
