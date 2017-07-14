package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.BrandRecheck;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ReCheckTaskAdapter extends BaseRecyclerAdapter<BrandRecheck> {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public ReCheckTaskAdapter(Context context, ArrayList<BrandRecheck> brandRechecks) {
        super(context, brandRechecks);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final BrandRecheck brandRecheck = mDatas.get(position);

        aItemViewHolder.mTvRecheckTitle.setText(brandRecheck.getBrandName());
        Date date = new Date(brandRecheck.getCreatedTimestamp() * 1000l);
        aItemViewHolder.mTvRecheckTime.setText(df.format(date));
        if (brandRecheck.getState() == 1) {
            aItemViewHolder.mTvRecheckStatus.setText("复查中");
        } else {
            aItemViewHolder.mTvRecheckStatus.setText("复查完成");
        }

        if (mItemClickListener != null) {
            aItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, null, position, 0);
                }
            });
        }
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_brand_recheck;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;

        public TextView mTvRecheckTitle, mTvRecheckTime, mTvRecheckStatus;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mTvRecheckTitle = (TextView) itemView.findViewById(R.id.tv_recheck_name);
            mTvRecheckTime = (TextView) itemView.findViewById(R.id.tv_recheck_time);
            mTvRecheckStatus = (TextView) itemView.findViewById(R.id.tv_recheck_status);
        }
    }
}
