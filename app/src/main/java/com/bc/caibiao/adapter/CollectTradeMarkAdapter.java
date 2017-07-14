package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.TrademarkFollow;
import com.bc.caibiao.ui.me.CollectTradeMarkActivity;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * 收藏的商标Adapter
 */

public class CollectTradeMarkAdapter extends BaseRecyclerAdapter<TrademarkFollow> {

    public CollectTradeMarkAdapter(Context context, ArrayList<TrademarkFollow> datas) {
        super(context, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_collect_mark;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;

        public SimpleDraweeView mTrademarkImage;
        public TextView mTvFollowId, mTvTrademarkName, mTvCollectCount, mCancelAttention,tv3;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mTrademarkImage = (SimpleDraweeView) itemView.findViewById(R.id.sdv_trademarkimage);
            mTvFollowId = (TextView) itemView.findViewById(R.id.tv_followid);
            mTvTrademarkName = (TextView) itemView.findViewById(R.id.tv_trademarkname);
            mTvCollectCount = (TextView) itemView.findViewById(R.id.tv_count);
            mCancelAttention = (TextView)itemView.findViewById(R.id.cancel_attention);
            tv3 = (TextView)itemView.findViewById(R.id.tv3);
        }
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final TrademarkFollow trademarkFollow = mDatas.get(position);

        if (trademarkFollow.getTrademark_image() != null) {
            ImageLoader.progressiveLoad(trademarkFollow.getTrademark_image(), aItemViewHolder.mTrademarkImage);
        }
        aItemViewHolder.mTvFollowId.setText(trademarkFollow.getTrademark_regno());
        aItemViewHolder.mTvTrademarkName.setText(trademarkFollow.getTrademark_tmname());
        aItemViewHolder.mTvCollectCount.setText(trademarkFollow.getTrademark_intcls());

        aItemViewHolder.tv3.setText(trademarkFollow.getTrademark_tmlaw());

        if (mItemClickListener != null) {
            aItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, null, position, 0);
                }
            });
        }

        aItemViewHolder.mCancelAttention.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mContext instanceof CollectTradeMarkActivity) {
                    ((CollectTradeMarkActivity) mContext).dismissFlow(position, trademarkFollow.getTrademark_regno(), trademarkFollow.getTrademark_intcls());
                }
            }
        });
    }

}
