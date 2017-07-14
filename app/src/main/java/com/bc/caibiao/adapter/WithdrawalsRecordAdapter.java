package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.MemberWithdrawCashApply;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WithdrawalsRecordAdapter extends BaseRecyclerAdapter<MemberWithdrawCashApply> {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public WithdrawalsRecordAdapter(Context mContext, ArrayList<MemberWithdrawCashApply> datas) {
        super(mContext, datas);
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_withdrawals_record;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final MemberWithdrawCashApply memberWithdrawCashApply = mDatas.get(position);

        aItemViewHolder.mTvAccount.setText(String.valueOf(memberWithdrawCashApply.getAlipayAccount()));

        switch (memberWithdrawCashApply.getState()) {
            case 1://0xffff871d
                aItemViewHolder.mTvState.setTextColor(0xffff871d);
                aItemViewHolder.mTvState.setText("处理中");
                aItemViewHolder.mTvReason.setVisibility(View.GONE);
                break;
            case 2://0xff62b848
                aItemViewHolder.mTvState.setTextColor(0xff62b848);
                aItemViewHolder.mTvState.setText("提现完成");
                aItemViewHolder.mTvReason.setVisibility(View.GONE);
                break;
            case 3://0xffe63f3f
            case 9://0xffe63f3f
                aItemViewHolder.mTvState.setTextColor(0xffe63f3f);
                aItemViewHolder.mTvState.setText("提现失败");
                aItemViewHolder.mTvReason.setVisibility(View.VISIBLE);
                aItemViewHolder.mTvReason.setText("原因：" + memberWithdrawCashApply.getRemark());
                break;
        }
        Date date = new Date(memberWithdrawCashApply.getCreatedTime() * 1000l);
        aItemViewHolder.mTvTime.setText(df.format(date));
        aItemViewHolder.mTvMoney.setText(String.valueOf(memberWithdrawCashApply.getApplyAmountYuan()) + "元");
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;
        TextView mTvAccount, mTvState, mTvReason, mTvTime, mTvMoney;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mTvAccount = (TextView) itemView.findViewById(R.id.tvAccount);
            mTvState = (TextView) itemView.findViewById(R.id.tvState);
            mTvReason = (TextView) itemView.findViewById(R.id.tvReason);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
        }
    }

}
