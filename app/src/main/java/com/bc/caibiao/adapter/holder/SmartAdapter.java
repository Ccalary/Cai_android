package com.bc.caibiao.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.HomePageModel.SignByHYOrKeyResult;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/23.
 */

public class SmartAdapter extends BaseRecyclerAdapter<SignByHYOrKeyResult> {

    public SmartAdapter(Context context, ArrayList<SignByHYOrKeyResult> datas) {
        super(context, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_smart;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final SignByHYOrKeyResult aRewardTaskModel = mDatas.get(position);

        aItemViewHolder.mTitle.setText(aRewardTaskModel.name);

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
        public TextView mTitle;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;
            mTitle = (TextView) arg0.findViewById(R.id.tv_na);
        }

    }
}
