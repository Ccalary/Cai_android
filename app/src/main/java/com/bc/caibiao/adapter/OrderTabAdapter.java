package com.bc.caibiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.Order;
import com.bc.caibiao.ui.me.MyOrderActivity;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.utils.ImageLoader;
import com.bc.caibiao.view.popupwindow.ChoicePayPop;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * 订单Adapter(平台服务和商标转让)
 */

public class OrderTabAdapter extends BaseRecyclerAdapter<Order> {
    public OrderTabAdapter(Context context, ArrayList<Order> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder aItemViewHolder = (ItemViewHolder) viewHolder;
        final Order order = mDatas.get(position);

        aItemViewHolder.mTvOrderId.setText("订单号ID: " + order.getOrder_number());
        aItemViewHolder.mTvOrderStatus.setText(order.getStatus());
        aItemViewHolder.mTvOrderdes.setText(order.getProducts().get(0).getName());
        aItemViewHolder.mTvOrderMomey.setText(order.getProducts().get(0).getPrice());
        aItemViewHolder.mTvOrderTime.setText("创建日期: " + order.getDate_added());
        if (order.getProducts().get(0).getThumb() != null) {
            ImageLoader.progressiveLoad(order.getProducts().get(0).getThumb(), aItemViewHolder.mOrderIcon);
        } else {
            aItemViewHolder.mOrderIcon.setBackgroundResource(R.drawable.home_banner_default);
        }

        if ("已支付".equals(order.getStatus())) {
            aItemViewHolder.mLlPayBtn.setVisibility(View.GONE);
            aItemViewHolder.mLlDetailBtn.setVisibility(View.GONE);
            aItemViewHolder.mLlDetailOneBtn.setVisibility(View.VISIBLE);
        } else {
            aItemViewHolder.mLlPayBtn.setVisibility(View.VISIBLE);
            aItemViewHolder.mLlDetailBtn.setVisibility(View.VISIBLE);
            aItemViewHolder.mLlDetailOneBtn.setVisibility(View.GONE);
        }
        if (mItemClickListener != null) {
            aItemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(null, null, position, 0);
                }
            });
        }

        aItemViewHolder.mLlPayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new ChoicePayPop(mContext, null, "", new ChoicePayPop.PayTypeCallback() {

                    @Override
                    public void doPayZFB() {
                        if(mContext instanceof MyOrderActivity) {
                            ((MyOrderActivity) mContext).payWithZFB(order.getOrder_id());
                        }
                    }

                    @Override
                    public void doPayWX() {
                        if(mContext instanceof MyOrderActivity) {
                            ((MyOrderActivity) mContext).payWithWX(order.getOrder_id());
                        }
                    }
                }).setTitle("选择支付方式").show();
            }
        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_myorder;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolderViewHolder(View arg0) {
        return new ItemViewHolder(arg0, this.mContext);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public View mView;

        public SimpleDraweeView mOrderIcon;
        public TextView mTvOrderId;
        public TextView mTvOrderStatus;
        public TextView mTvOrderTime;
        public TextView mTvOrderdes;
        public TextView mTvOrderMomey;
        public LinearLayout mLlPayBtn;
        public LinearLayout mLlDetailBtn;
        public LinearLayout mLlDetailOneBtn;

        public ItemViewHolder(View arg0, Context aContext) {
            super(arg0);
            this.mContext = aContext;
            mView = arg0;

            mTvOrderId = (TextView) itemView.findViewById(R.id.order_id);
            mTvOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
            mTvOrderdes = (TextView) itemView.findViewById(R.id.order_des);
            mTvOrderMomey = (TextView) itemView.findViewById(R.id.order_money);
            mTvOrderTime = (TextView) itemView.findViewById(R.id.order_time);
            mOrderIcon = (SimpleDraweeView) itemView.findViewById(R.id.order_icon);
            mLlPayBtn = (LinearLayout) itemView.findViewById(R.id.order_pay);
            mLlDetailBtn = (LinearLayout) itemView.findViewById(R.id.order_detail);
            mLlDetailOneBtn = (LinearLayout) itemView.findViewById(R.id.order_detail_one);
        }
    }


}
