package com.bc.caibiao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.MemberAccountFlow;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WalletAdapter extends BaseRecyclerAdapter<MemberAccountFlow> {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    public WalletAdapter(Context mContext, ArrayList<MemberAccountFlow> datas) {
        super(mContext, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_wallet;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        // 处理越界
        if (position >= mDatas.size()) {
            return;
        }
        final MemberAccountFlow memberAccountFlow = mDatas.get(position);

        aItemViewHolder.mTvTitle.setText(memberAccountFlow.getReason());
        switch (memberAccountFlow.getFlowType()) {
            case 1:
                aItemViewHolder.mTvMoney.setTextColor(Color.parseColor("#329d2f"));
                aItemViewHolder.mTvMoney.setText("- ¥ " + memberAccountFlow.getFlowAmountYuan());
                break;
            case 2:
            case 3:
                aItemViewHolder.mTvMoney.setTextColor(Color.parseColor("#fe0000"));
                aItemViewHolder.mTvMoney.setText("+ ¥ " + memberAccountFlow.getFlowAmountYuan());
                break;
        }
        Date date = new Date(memberAccountFlow.getCreatedTime() * 1000l);
        aItemViewHolder.mTvTime.setText(df.format(date));
        aItemViewHolder.mTvBalance.setText(" ¥ " + memberAccountFlow.getAccountBalanceYuan());

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;

        TextView mTvTitle, mTvTime, mTvMoney, mTvBalance;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvBalance = (TextView) itemView.findViewById(R.id.tvBalance);
        }
    }
}
